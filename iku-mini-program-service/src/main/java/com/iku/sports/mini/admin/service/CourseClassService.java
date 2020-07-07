package com.iku.sports.mini.admin.service;


import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.ClassOverview;

import java.util.List;

/**
 * File: CourseClassService
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:Class Service
 * CopyRight: All rights reserved
 **/
public interface CourseClassService {

    List<CourseClass> paginateClasses(short courseId, int curPage) throws ApiServiceException;

    ClassOverview getClassOverviewById(final int id) throws ApiServiceException;

    void incrementWatchesByClassId(int id) throws ApiServiceException;

    /**
     *
     * @param relatedClassId
     * @return
     * @throws ApiServiceException
     * @Author: DorSey
     */
    List<CourseClass> getPromotionsById(int relatedClassId) throws ApiServiceException;

    List<CourseClass> getClassesByUserIdAndFavoriteType(String userId, int favoriteType) throws ApiServiceException;

    List<CourseClass> getClassesByCoachId(int coachId) throws ApiServiceException;

    List<CourseClass> getClassesByUserId(String userId) throws ApiServiceException;

    void saveWatchedClasses(String userId, int classId) throws ApiServiceException;

    boolean existsWatchedHis(String userId, int classId) throws ApiServiceException;

    int countWatchedCourseClass(String userId, Integer courseId);
}
