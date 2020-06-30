/**
 * File: CourseServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 15:07
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.CourseRepository;
import com.iku.sports.mini.admin.service.CourseService;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
            course.setAvatar(Utils.join(config.getStaticResourceServer(), course.getAvatar(), Constants.FORWARD_SLASH));
            course.setFee(course.getFee().setScale(2, RoundingMode.HALF_UP));
        });

        return courses;
    }

}
