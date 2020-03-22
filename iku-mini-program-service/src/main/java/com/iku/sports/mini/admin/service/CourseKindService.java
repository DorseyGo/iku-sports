/**
 * File: CourseKindService
 * Author: DorSey Q F TANG
 * Created: 2020/3/22 10:59
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.entity.CourseKind;

import java.util.List;

public interface CourseKindService {
    List<CourseKind> getCourseKindsByCategoryId(final short categoryId);
}
