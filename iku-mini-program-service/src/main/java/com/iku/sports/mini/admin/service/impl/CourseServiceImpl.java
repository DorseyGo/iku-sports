/**
 * File: CourseServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 15:07
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.CourseRepository;
import com.iku.sports.mini.admin.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.List;

/**
 * The default implementation of {@link CourseService}.
 */
@Slf4j
@Service("courseService")
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final IkuSportsConfig config;

    @Autowired
    public CourseServiceImpl(@Qualifier("courseRepository") final CourseRepository courseRepository,
            final IkuSportsConfig config) {
        this.courseRepository = courseRepository;
        this.config = config;
    }

    @Override
    public List<Course> getCoursesByCategoryId(final short categoryId) {
        log.debug("==> Fetch courses according to category ID: {}", categoryId);
        final List<Course> courses = courseRepository.findCoursesByCategoryId(categoryId);
        courses.forEach(course -> {
            postProcess(course);
        });

        return courses;
    }

    @Override
    public Course getCourseById(final short courseId) throws ApiServiceException {
        try {
            final Course course = courseRepository.findCourseById(courseId);
            postProcess(course);

            return course;
        } catch (DataAccessException e) {
            log.error("==> Failed to retrieve course by ID: {}", courseId, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }

    @Override
    public List<Course> getCourses(List<Short> courseIds) {
        List<Course> courses = courseRepository.findCoursesByIds(courseIds);
        courses.forEach(this::postProcess);

        return courses;
    }

    private void postProcess(final Course course) {
        course.setFee(course.getFee().divide(Constants.ONE_HUNDRED).setScale(2, RoundingMode.HALF_UP));
    }
}
