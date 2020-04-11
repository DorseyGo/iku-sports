package com.iku.sports.mini.admin.service;


import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.model.ClassCount;

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

    CourseClass getClassById(int id) throws Exception;

    List<CourseClass> getTop3PopularClasses(short categoryId) throws Exception;

    List<CourseClass> getTop3ClassicByCategoryId(short categoryId,int days) throws Exception;

    List<CourseClass> getClassesByCourseId(short courseId) throws Exception;

    ClassCount getTotalNumMoneyByCourseId(int courseId) throws Exception;
}
