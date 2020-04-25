package com.iku.sports.mini.admin.service.impl;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.ClassCount;
import com.iku.sports.mini.admin.model.ClassOverview;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.CourseClassRepository;
import com.iku.sports.mini.admin.service.CourseClassService;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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
    private final IkuSportsConfig config;

    @Autowired
    public CourseClassServiceImpl(@Qualifier("courseClassRepository") CourseClassRepository courseClassRepository,
            IkuSportsConfig config) {
        this.courseClassRepository = courseClassRepository;
        this.config = config;
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
    public ClassOverview getClassOverviewById(int id) throws ApiServiceException {
        try {
            return courseClassRepository.getClassOverviewById(id);
        } catch (DataAccessException e) {
            log.error("Failed to get class overview by id: {}", id, e);
            throw new ApiServiceException(IkuSportsError.REQ_RESOURCE_NOT_FOUND_ERR);
        }
    }

    @Override
    public List<CourseClass> getTop3PopularClasses(short categoryId) throws Exception {
        return courseClassRepository.getTop3PopularClasses(categoryId);
    }

    @Override
    public List<CourseClass> getTop3ClassicByCategoryId(short categoryId, int days) throws Exception {
        return courseClassRepository.getTop3ClassicByCategoryId(categoryId, days);
    }

    @Override
    public List<CourseClass> getClassesByCourseId(short courseId) throws Exception {
        return courseClassRepository.getClassByCourseId(courseId);
    }

    @Override
    public ClassCount getTotalNumMoneyByCourseId(int courseId) throws Exception {
        return courseClassRepository.getTotalNumMoneyByCourseId(courseId);
    }

    @Override
    public void setClassWatchesById(int id) throws Exception {
        courseClassRepository.setClassWatchesById(id);
    }

    @Override
    public List<CourseClass> getPromotionsById(int relatedClassId) throws ApiServiceException {
        final List<CourseClass> classes = courseClassRepository.findFirst2ByClassId(relatedClassId);
        classes.forEach(courseClass -> {
            courseClass.setCover(
                    Utils.join(config.getStaticResourceServer(), courseClass.getCover(), Constants.FORWARD_SLASH));
        });

        return classes;
    }
}
