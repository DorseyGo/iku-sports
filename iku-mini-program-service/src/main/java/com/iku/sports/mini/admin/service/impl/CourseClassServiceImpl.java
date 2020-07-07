package com.iku.sports.mini.admin.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.ClassOverview;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.CourseClassRepository;
import com.iku.sports.mini.admin.service.CourseClassService;
import com.iku.sports.mini.admin.service.WatchedClassHisService;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * File: CourseClassServiceImpl
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:Implements CourseClassService
 * CopyRight: All rights reserved
 **/
@Slf4j
@Transactional
@Service("courseClassService")
public class CourseClassServiceImpl implements CourseClassService {

    private static final List<Integer> ALLOWED_PAGE_SIZES = Lists.newArrayList(5, 10, 20);

    private final CourseClassRepository courseClassRepository;
    private final IkuSportsConfig config;
    private final WatchedClassHisService watchedClassHisService;

    @Autowired
    public CourseClassServiceImpl(@Qualifier("courseClassRepository") CourseClassRepository courseClassRepository,
            IkuSportsConfig config,
            @Qualifier("watchedClassHisService") WatchedClassHisService watchedClassHisService) {
        this.courseClassRepository = courseClassRepository;
        this.config = config;
        this.watchedClassHisService = watchedClassHisService;
    }


    @Override
    public List<CourseClass> paginateClasses(short courseId, int curPage) throws ApiServiceException {
        int pageSize = config.getPageSize();
        if (!ALLOWED_PAGE_SIZES.contains(pageSize)) {
            log.info("Page size fallback, due to passed-in page size: {}, not contained in: {}",
                    pageSize, ALLOWED_PAGE_SIZES);

            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        final int offset = Utils.paginateOffset(curPage, pageSize);
        try {
            return courseClassRepository.paginateClasses(courseId, offset, pageSize);
        } catch (DataAccessException e) {
            log.error("Failed to pagination the classes by course id: {}, offset: {}, page size: {}", courseId, offset,
                    pageSize, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }

    @Override
    public ClassOverview getClassOverviewById(int id) throws ApiServiceException {
        try {
            ClassOverview overview = courseClassRepository.findClassOverviewById(id);
            if (overview.getVideoUrl() != null) {
                overview.setVideoUrl(
                        Utils.join(config.getStaticResourceServer(), overview.getVideoUrl(), Constants.FORWARD_SLASH));
            }

            return overview;
        } catch (DataAccessException e) {
            log.error("Failed to get class overview by id: {}", id, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void incrementWatchesByClassId(int id) throws ApiServiceException, DataAccessException {
        courseClassRepository.incrementWatchesByClassId(id);
    }

    @Override
    public List<CourseClass> getPromotionsById(int relatedClassId) throws ApiServiceException {
        try {
            final List<CourseClass> classes = courseClassRepository.findFirst2ByClassId(relatedClassId);
            classes.forEach(courseClass -> {
                if (!Strings.isNullOrEmpty(courseClass.getCover())) {
                    courseClass.setCover(
                            Utils.join(config.getStaticResourceServer(), courseClass.getCover(),
                                    Constants.FORWARD_SLASH));
                }
            });

            return classes;
        } catch (DataAccessException e) {
            log.error("Failed to get promotions by related class ID: {}", relatedClassId, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }

    @Override
    public List<CourseClass> getClassesByUserIdAndFavoriteType(String userId, int favoriteType) throws
            ApiServiceException {
        try {
            final List<CourseClass> courseClasses = courseClassRepository
                    .findClassesByUserIdAndFavoriteType(userId, favoriteType);
            courseClasses.forEach(courseClass -> {
                if (!Strings.isNullOrEmpty(courseClass.getCover())) {

                    courseClass.setCover(
                            Utils.join(config.getStaticResourceServer(), courseClass.getCover(),
                                    Constants.FORWARD_SLASH));
                }
            });

            return courseClasses;
        } catch (DataAccessException e) {
            log.error("Failed to get class by user id: {} and favorite type: {}", userId, favoriteType, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }

    @Override
    public List<CourseClass> getClassesByCoachId(int coachId) throws ApiServiceException {
        try {
            final List<CourseClass> courseClasses = courseClassRepository
                    .findClassesByCoachId(coachId);
            courseClasses.forEach(courseClass -> {
                if (!Strings.isNullOrEmpty(courseClass.getCover())) {
                    courseClass.setCover(
                            Utils.join(config.getStaticResourceServer(), courseClass.getCover(),
                                    Constants.FORWARD_SLASH));
                }
            });

            return courseClasses;
        } catch (DataAccessException e) {
            log.error("Failed to retrieve course class by coach ID: {}", coachId, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }

    @Override
    public List<CourseClass> getClassesByUserId(String userId) throws ApiServiceException {
        try {
            final List<CourseClass> courseClasses = courseClassRepository.findWatchedClassesByUserId(userId);
            courseClasses.forEach(courseClass -> {
                if (!Strings.isNullOrEmpty(courseClass.getCover())) {
                    courseClass.setCover(
                            Utils.join(config.getStaticResourceServer(), courseClass.getCover(),
                                    Constants.FORWARD_SLASH));
                }
            });

            return courseClasses;
        } catch (DataAccessException e) {
            log.error("Failed to retrieve watched classes by user Id: {}", userId, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    public void saveWatchedClasses(String userId, int classId) throws ApiServiceException {
        courseClassRepository.saveWatchedClasses(userId, classId);
    }

    @Override
    public boolean existsWatchedHis(String userId, int classId) throws ApiServiceException {
        return watchedClassHisService.existsWatchedHis(userId, classId);
    }

    @Override
    public int countWatchedCourseClass(String userId, Integer courseId) {
        try {
            List<CourseClass> watchedClasses = this.getClassesByUserId(userId);
            if (CollectionUtils.isEmpty(watchedClasses)) {
                log.warn("the user:{} has not watched class", userId);
                return 0;
            }

            Map<Integer, Long> countWatchedClasses = watchedClasses
                    .stream()
                    .collect(Collectors.groupingBy(CourseClass::getCourseId, Collectors.counting()));
            return countWatchedClasses.getOrDefault(courseId, 0L).intValue();
        } catch (ApiServiceException e) {
            return 0;
        }
    }
}
