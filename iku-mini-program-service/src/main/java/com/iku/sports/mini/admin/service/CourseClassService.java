package com.iku.sports.mini.admin.service;


import com.iku.sports.mini.admin.entity.CourseClass;

import java.util.List;

/**
 * File: CourseClassService
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:Class Service
 * CopyRight: All rights reserved
 **/
public interface CourseClassService {
    List<CourseClass> getFirst3ClassesByCourseId(short courseId);
    List<CourseClass> paginateClasses(short courseId,int offset,int pageSize);
    CourseClass getClassById(int id);
    List<CourseClass> getTop3PopularClasses(short categoryId);
    List<CourseClass> getTop3ClassicByCategoryId(short categoryId,int days);



}
