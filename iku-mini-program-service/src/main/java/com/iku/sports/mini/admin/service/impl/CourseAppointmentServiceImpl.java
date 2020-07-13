package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Appointment;
import com.iku.sports.mini.admin.entity.Category;
import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.CourseAppoint;
import com.iku.sports.mini.admin.repository.CourseAppointRepository;
import com.iku.sports.mini.admin.service.*;
import com.iku.sports.mini.admin.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseAppointmentServiceImpl implements CourseAppointmentService {
    private OrderService orderService;
    private CourseService courseService;
    private CourseClassService courseClassService;
    private CategoryService categoryService;
    private CourseAppointRepository courseAppointRepository;

    @Autowired
    public CourseAppointmentServiceImpl(OrderService orderService, CourseService courseService,
                                        CourseClassService courseClassService, CategoryService categoryService,
                                        CourseAppointRepository courseAppointRepository) {
        this.orderService = orderService;
        this.courseService = courseService;
        this.courseClassService = courseClassService;
        this.categoryService = categoryService;
        this.courseAppointRepository = courseAppointRepository;
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
                                .studiedClass(courseClassService.countWatchedCourseClass(userId, (int) course.getId()))
                                .build();
                    })
                    .collect(Collectors.toList());
    }

    @Override
    public Appointment appointedClass(String userId, Integer arrangedClassId) {
        return courseAppointRepository.findAppointedClass(userId, arrangedClassId);
    }
}
