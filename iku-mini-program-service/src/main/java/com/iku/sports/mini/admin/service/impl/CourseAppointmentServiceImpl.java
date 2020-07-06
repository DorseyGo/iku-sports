package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.CourseAppoint;
import com.iku.sports.mini.admin.service.CourseAppointmentService;
import com.iku.sports.mini.admin.service.CourseClassService;
import com.iku.sports.mini.admin.service.CourseService;
import com.iku.sports.mini.admin.service.OrderService;
import com.iku.sports.mini.admin.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseAppointmentServiceImpl implements CourseAppointmentService {
    private OrderService orderService;
    private CourseService courseService;
    private CourseClassService courseClassService;

    @Autowired
    public CourseAppointmentServiceImpl(OrderService orderService, CourseService courseService,
                                        CourseClassService courseClassService) {
        this.orderService = orderService;
        this.courseService = courseService;
        this.courseClassService = courseClassService;
    }

    @Override
    public List<CourseAppoint> courseAppoint(String userId) throws ApiServiceException {
        List<Integer> courseIds = orderService.findPurchasedCourse(userId);
        if (CollectionUtils.isEmpty(courseIds)) {
            return Collections.emptyList();
        }

        List<Course> courses = courseService.getCourses(courseIds);
        if (CollectionUtils.isEmpty(courses)) {
            log.warn("cannot find course with course id:{}", JsonUtil.toJSONString(courseIds));
            return Collections.emptyList();
        }

        return courses.stream()
                    .map(course -> {
                        return CourseAppoint.builder()
                                .courseId((int) course.getId())
                                .courseLevel(course.getLevel())
                                .courseName(course.getName())
                                .courseDesc(course.getDescription())
                                .totalClass(course.getNumClasses())
                                .studiedClass(0) // TODO
                                .build();
                    })
                    .collect(Collectors.toList());
    }
}
