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
    List<CourseClass> getAllClasses();
    List<CourseClass> getTop3ClassesByCourseId(short courseId);
    List<CourseClass> paginateClasses(short courseId,int offset,int pageSize);
    CourseClass getClassById(int id);

}
