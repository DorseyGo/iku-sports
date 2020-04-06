/**
 * File: CourseServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 15:07
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.google.common.base.Strings;
import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.exception.ApiInvokedException;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.CourseRepository;
import com.iku.sports.mini.admin.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service("courseService")
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(@Qualifier("courseRepository") final CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getCoursesByCategoryId(final short categoryId) {
        log.debug("Fetch courses according to category ID: {}", categoryId);

        return courseRepository.findCoursesByCategoryId(categoryId);
    }

    @Override
    public List<Course> getCoursesByCategoryName(String categoryName) throws ApiInvokedException {
        if (Strings.isNullOrEmpty(categoryName)) {
            throw new ApiInvokedException("Category name not specified");
        }

        try {
            return courseRepository.findCoursesByCategoryName(categoryName);
        } catch (SQLException e) {
            log.error("Failed to find course by category name: {}", categoryName, e);
            throw new ApiInvokedException(e);
        }
    }

    @Override
    public Course getCourseByCourseId(short courseId) throws ApiInvokedException {
        try {
            final Course course = courseRepository.findCourseById(courseId);
            course.setCharge(BigDecimal.valueOf(course.getFee())
                                     .divide(BigDecimal.valueOf(100), Constants.DIVIDE_SCALE,
                                             BigDecimal.ROUND_HALF_UP));

            return course;
        } catch (SQLException e) {
            log.error("Failed to find course by id: {}", courseId);
            throw new ApiInvokedException(e);
        }
    }

}
