/**
 * File: CourseService
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 15:07
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.model.Overview;

import java.util.List;

public interface CourseService {
    List<Course> getCoursesByCategoryId(final short categoryId);

    Overview getCourseOverviewByCategoryId(final short categoryId) throws Exception;
}
