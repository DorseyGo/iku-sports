/**
 * File: CourseService
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 15:07
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.exception.ApiServiceException;

import java.util.List;

/**
 * The service, which is used to handle the business logic for resource <tt>course</tt>.
 */
public interface CourseService {

    /**
     * Returns a list of courses, which retrieved according to the specific category.
     *
     * @param categoryId the category ID, which identifies the category.
     * @return a list of courses.
     */
    List<Course> getCoursesByCategoryId(final short categoryId);
}
