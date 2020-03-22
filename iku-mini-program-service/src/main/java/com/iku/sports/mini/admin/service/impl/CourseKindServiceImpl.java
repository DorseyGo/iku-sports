/**
 * File: CourseKindServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/3/22 11:00
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.google.common.collect.Maps;
import com.iku.sports.mini.admin.entity.CourseKind;
import com.iku.sports.mini.admin.repository.CourseKindRepository;
import com.iku.sports.mini.admin.service.CourseKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("courseKindService")
public class CourseKindServiceImpl implements CourseKindService {
    private static final Map<Short, List<CourseKind>> CACHE = Maps.newHashMapWithExpectedSize(3);
    private final CourseKindRepository courseKindRepository;

    @Autowired
    public CourseKindServiceImpl(@Qualifier("courseKindRepository") final CourseKindRepository courseKindRepository) {
        this.courseKindRepository = courseKindRepository;
    }

    @Override
    public List<CourseKind> getCourseKindsByCategoryId(final short categoryId) {
        List<CourseKind> courseKinds = CACHE.get(categoryId);
        if (courseKinds != null) {
            return courseKinds;
        }

        courseKinds = courseKindRepository.findByCategoryId(categoryId);
        CACHE.put(categoryId, courseKinds);

        return courseKinds;
    }
}
