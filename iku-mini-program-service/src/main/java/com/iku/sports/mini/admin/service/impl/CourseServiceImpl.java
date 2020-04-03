/**
 * File: CourseServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 15:07
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iku.sports.mini.admin.entity.Activity;
import com.iku.sports.mini.admin.entity.Category;
import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.model.Overview;
import com.iku.sports.mini.admin.repository.CourseRepository;
import com.iku.sports.mini.admin.service.ActivityService;
import com.iku.sports.mini.admin.service.CategoryService;
import com.iku.sports.mini.admin.service.CourseClassService;
import com.iku.sports.mini.admin.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service("courseService")
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseClassService courseClassService;
    private final ActivityService activityService;
    private final CategoryService categoryService;


    @Autowired
    public CourseServiceImpl(@Qualifier("courseRepository") final CourseRepository courseRepository,
            @Qualifier("courseClassService") final CourseClassService courseClassService,
            @Qualifier("activityService") final ActivityService activityService,
            @Qualifier("categoryService") final CategoryService categoryService) {
        this.courseRepository = courseRepository;
        this.courseClassService = courseClassService;
        this.activityService = activityService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Course> getCoursesByCategoryId(final short categoryId) {
        log.debug("Fetch courses according to category ID: {}", categoryId);

        return courseRepository.findCoursesByCategoryId(categoryId);
    }

    @Override
    public Overview getCourseOverviewByCategoryId(final short categoryId) throws Exception {
        log.debug("Fetch course overview by category id: {}", categoryId);

        final Category category = categoryService.getCategoryById(categoryId);
        final List<Activity> activities = activityService.getFirst3ActivitiesByCategoryId(categoryId);
        final Map<Short, List<CourseClass>> levelClasses = Maps.newHashMap();
        final List<Course> courses = getCoursesByCategoryId(categoryId);
        courses.forEach(course -> {
            short courseId = course.getId();
            List<CourseClass> courseClasses = null;
            try {
                courseClasses = courseClassService.getFirst3ClassesByCourseId(courseId);
            } catch (Exception e) {
                courseClasses = Lists.newArrayListWithExpectedSize(0);
            }

            levelClasses.put(courseId, courseClasses);
        });

        return Overview.builder()
                .categoryName(category.getName())
                .activities(activities)
                .courseClasses(levelClasses)
                .build();
    }
}
