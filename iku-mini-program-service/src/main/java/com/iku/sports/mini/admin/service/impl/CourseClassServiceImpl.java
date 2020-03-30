package com.iku.sports.mini.admin.service.impl;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.repository.CourseClassRepository;
import com.iku.sports.mini.admin.service.CourseClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * File: CourseClassServiceImpl
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:Implements CourseClassService
 * CopyRight: All rights reserved
 **/
@Slf4j
@Service("courseClassService")
public class CourseClassServiceImpl implements CourseClassService {
    private final CourseClassRepository courseClassRepository;
    private final List<Integer> ALLOWED_PAGE_SIZES = Lists.newArrayList(10, 20, 50, 100);
    static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    public CourseClassServiceImpl(@Qualifier("courseClassRepository") CourseClassRepository courseClassRepository) {
        this.courseClassRepository = courseClassRepository;
    }


    @Override
    public List<CourseClass> getFirst3ClassesByCourseId(short courseId) throws Exception {
        return courseClassRepository.getFirst3ClassesByCourseId(courseId);
    }

    @Override
    public List<CourseClass> paginateClasses(short courseId, int offset, int pageSize) throws Exception {
        if (!ALLOWED_PAGE_SIZES.contains(pageSize)) {
            log.info("Page size fallback, due to passed-in page size: {}, not contained in: {}",
                    pageSize, ALLOWED_PAGE_SIZES);

            pageSize = DEFAULT_PAGE_SIZE;
        }

        return courseClassRepository.paginateClasses(courseId, offset, pageSize);
    }

    @Override
    public CourseClass getClassById(int id) throws Exception {
        return courseClassRepository.getClassById(id);
    }

    @Override
    public List<CourseClass> getTop3PopularClasses(short categoryId) throws Exception {
        return courseClassRepository.getTop3PopularClasses(categoryId);
    }

    @Override
    public List<CourseClass> getTop3ClassicByCategoryId(short categoryId, int days) throws Exception {
        return courseClassRepository.getTop3ClassicByCategoryId(categoryId, days);
    }
}
