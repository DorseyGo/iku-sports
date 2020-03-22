/**
 * File: CourseServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/3/21 23:23
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Activity;
import com.iku.sports.mini.admin.entity.Category;
import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.entity.CourseKind;
import com.iku.sports.mini.admin.model.Overview;
import com.iku.sports.mini.admin.repository.ActivityRepository;
import com.iku.sports.mini.admin.repository.CourseRepository;
import com.iku.sports.mini.admin.service.CategoryService;
import com.iku.sports.mini.admin.service.CourseKindService;
import com.iku.sports.mini.admin.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ActivityRepository activityRepository;
    private final CategoryService categoryService;
    private final CourseKindService courseKindService;

    @Autowired
    public CourseServiceImpl(@Qualifier("courseRepository") final CourseRepository courseRepository,
            @Qualifier("activityRepository") final ActivityRepository activityRepository,
            @Qualifier("categoryService") final CategoryService categoryService,
            @Qualifier("courseKindService") final CourseKindService courseKindService) {
        this.courseRepository = courseRepository;
        this.activityRepository = activityRepository;
        this.categoryService = categoryService;
        this.courseKindService = courseKindService;
    }

    @Override
    public Overview getCourseOverviewByCategoryId(final short categoryId) {
        final Category category = categoryService.getCategoryById(categoryId);
        final List<Activity> activities = activityRepository.findFirst3ByCategoryIdOrderByCreateTimeDesc(categoryId);
        final List<CourseKind> courseKinds = courseKindService.getCourseKindsByCategoryId(categoryId);
        // FIXME how to get popular or classic courses
        final List<Course> courses = courseRepository.findAll(new JoinedAndWithCategoryIdSet(categoryId));

        final Overview overview = new Overview();
        overview.setCategoryName(category.getName());
        overview.setActivities(activities);
        overview.setCourseKinds(courseKinds);
        overview.setPopularCourses(courses);
        overview.setClassicCourses(courses);

        return overview;
    }

    class JoinedAndWithCategoryIdSet implements Specification<Course> {

        private final short categoryId;

        public JoinedAndWithCategoryIdSet(final short categoryId) {
            this.categoryId = categoryId;
        }

        @Nullable
        @Override
        public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {
            final Join<Course, CourseKind> join = root.join("courseKind", JoinType.INNER);
            Path<Object> categoryExpr = join.get("categoryId");

            return criteriaBuilder.and(criteriaBuilder.equal(categoryExpr, categoryId));
        }
    }
}
