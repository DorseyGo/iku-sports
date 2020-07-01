/**
 * File: CourseController
 * Author: DorSey Q F TANG
 * Created: 2020/3/29 12:51
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.annotation.WebLog;
import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(@Qualifier("courseService") final CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Fetch the list of courses according to the specific category.
     *
     * @param categoryId the category ID, which specified the category.
     * @return the list of courses.
     */
    @WebLog(response = false)
    @GetMapping("/category/{categoryId}/courses")
    public Response<List<Course>> getCoursesByCategoryId(@PathVariable("categoryId") final short categoryId) throws
            ApiServiceException {
        final List<Course> courses = courseService.getCoursesByCategoryId(categoryId);
        return Response.ok(courses);
    }

}
