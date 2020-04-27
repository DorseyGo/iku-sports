package com.iku.sports.mini.admin.service;


import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.ClassCount;
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
    List<CourseClass> getFirst3ClassesByCourseId(short courseId) throws Exception;

    List<CourseClass> paginateClasses(short courseId,int offset,int pageSize) throws Exception;

    ClassOverview getClassOverviewById(final int id) throws ApiServiceException;

    List<CourseClass> getTop3PopularClasses(short categoryId) throws Exception;

    List<CourseClass> getTop3ClassicByCategoryId(short categoryId,int days) throws Exception;

    List<CourseClass> getClassesByCourseId(short courseId) throws Exception;

    ClassCount getTotalNumMoneyByCourseId(int courseId) throws Exception;

    void setClassWatchesById(int id) throws Exception;

    /**
     *
     * @param relatedClassId
     * @return
     * @throws ApiServiceException
     * @Author: DorSey
     */
    List<CourseClass> getPromotionsById(int relatedClassId) throws ApiServiceException;

    List<CourseClass> getClassesByUserIdAndFavoriteType(String userId, int favoriteType) throws ApiServiceException;
}
