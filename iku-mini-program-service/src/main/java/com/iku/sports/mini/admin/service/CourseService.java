/**
 * File: CourseService
 * Author: DorSey Q F TANG
 * Created: 2020/3/21 23:22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.model.Overview;

public interface CourseService {
    Overview getCourseOverviewByCategoryId(short categoryId);
}
