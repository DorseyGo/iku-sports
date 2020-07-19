package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.*;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.CourseAppoint;
import com.iku.sports.mini.admin.repository.ArrangeClassRepository;
import com.iku.sports.mini.admin.repository.CourseAppointRepository;
import com.iku.sports.mini.admin.request.AppointClassRequest;
import com.iku.sports.mini.admin.service.*;
import com.iku.sports.mini.admin.utils.DateUtil;
import com.iku.sports.mini.admin.utils.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class CourseAppointmentServiceImpl implements CourseAppointmentService {
    @Value("${iku.mini-program.studied_class_days:7}")
    private String recentlyStudiedClass;

    private OrderService orderService;
    private CourseService courseService;
    private CourseClassService courseClassService;
    private CategoryService categoryService;
    private CourseAppointRepository courseAppointRepository;
    private ArrangeClassRepository arrangeClassRepository;
    private MessageNotifyService messageNotifyService;

    @Autowired
    public CourseAppointmentServiceImpl(OrderService orderService, CourseService courseService,
                                        CourseClassService courseClassService, CategoryService categoryService,
                                        CourseAppointRepository courseAppointRepository, ArrangeClassRepository arrangeClassRepository,
                                        MessageNotifyService messageNotifyService) {
        this.orderService = orderService;
        this.courseService = courseService;
        this.courseClassService = courseClassService;
        this.categoryService = categoryService;
        this.courseAppointRepository = courseAppointRepository;
        this.arrangeClassRepository = arrangeClassRepository;
        this.messageNotifyService = messageNotifyService;
    }

    @Override
    public List<CourseAppoint> courseAppoint(String userId) throws ApiServiceException {
        List<Short> courseIds = orderService.getPurchasedCourseIdsByUserId(userId);
        if (CollectionUtils.isEmpty(courseIds)) {
            return Collections.emptyList();
        }

        List<Course> courses = courseService.getCourses(courseIds);
        if (CollectionUtils.isEmpty(courses)) {
            log.warn("cannot find course with course id:{}", JsonUtil.toJSONString(courseIds));
            return Collections.emptyList();
        }

        List<Category> allCategories = categoryService.getAllCategories();
        Map<Short, String> categoryMap = allCategories.stream()
                .collect(Collectors.toMap(Category::getId, Category::getName));

        return courses.stream()
                    .map(course -> {
                        return CourseAppoint.builder()
                                .courseId((int) course.getId())
                                .categoryName(categoryMap.getOrDefault(course.getCategoryId(), "basketball"))
                                .courseLevel(course.getLevel())
                                .courseName(course.getName())
                                .courseDesc(course.getDescription())
                                .totalClass(course.getNumClasses())
                                .studiedClass(courseAppointRepository.findUserStudiedCourseClassByUserIdAndCourseId(userId, course.getId()))
                                .build();
                    })
                    .collect(Collectors.toList());
    }

    @Override
    public Appointment appointedClass(String userId, Integer arrangedClassId) {
        return courseAppointRepository.findAppointedClass(userId, arrangedClassId);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void appointment(AppointClassRequest appointClassRequest) {
        courseAppointRepository.appointment(appointClassRequest.getUserId(), appointClassRequest.getArrangeClassId());
        arrangeClassRepository.updateAppointedCount(appointClassRequest.getArrangeClassId(), 1);

        ArrangeClass arrangeClass = arrangeClassRepository.findById(appointClassRequest.getArrangeClassId());
        MessageNotify messageNotify = MessageNotify.appointmentMessageNotify(appointClassRequest.getUserId(),
                arrangeClass.getBeginTime(), arrangeClass.getSite(), arrangeClass.getId());
        messageNotifyService.sendMessageNotify(messageNotify);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void cancelAppointment(AppointClassRequest appointClassRequest) {
        courseAppointRepository.cancelAppointment(appointClassRequest.getUserId(), appointClassRequest.getArrangeClassId());
        arrangeClassRepository.updateAppointedCount(appointClassRequest.getArrangeClassId(), -1);
        messageNotifyService.cancelMessageNotifyByReceiverId(appointClassRequest.getUserId(), appointClassRequest.getArrangeClassId());
    }

    @Override
    public int countUserAppointment(String userId) {
        List<Appointment> appointments = courseAppointRepository.countUserAppointment(userId);
        if (CollectionUtils.isEmpty(appointments)) {
            return 0;
        }

        return appointments.size();
    }

    @Override
    public int countUserNotAppoint(String userId) {
        // 用户购买课程
        List<Short> courseIds = orderService.getPurchasedCourseIdsByUserId(userId);
        if (CollectionUtils.isEmpty(courseIds)) {
            // 用户没有购买课程
            return 0;
        }

        // 用户购买的课程信息
        List<Course> courses = courseService.getCourses(courseIds);
        if (CollectionUtils.isEmpty(courses)) {
            log.warn("cannot find course with course id:{}", JsonUtil.toJSONString(courseIds));
            return 0;
        }
        // 总课时数
        int totalPurchasedClassNums = courses.stream()
                .mapToInt(Course::getNumClasses)
                .sum();

        // 用户预约
        List<Appointment> userAppointments = courseAppointRepository.countUserAppointment(userId);
        if (CollectionUtils.isEmpty(userAppointments)) {
            // 用户没有预约
            return totalPurchasedClassNums;
        }

        return totalPurchasedClassNums - userAppointments.size();
    }

    @Override
    public List<CourseClass> userStudiedClass(String userId) throws ApiServiceException {
        List<Integer> userStudiedClassIds = courseAppointRepository.findUserStudiedClassIds(userId,
                DateUtil.aheadOf(Long.parseLong(recentlyStudiedClass)));
        if (CollectionUtils.isEmpty(userStudiedClassIds)) {
            // 用户最近没有已学课程
            return Collections.emptyList();
        }

        return courseClassService.getClassesById(userStudiedClassIds);
    }

    @Override
    public void completedClass() {
        courseAppointRepository.completedClass();
    }

    @Override
    public void userAttendClass(Date aheadMinutesConfirmAppoint) {
        courseAppointRepository.userAttendClass(aheadMinutesConfirmAppoint);
    }
}
