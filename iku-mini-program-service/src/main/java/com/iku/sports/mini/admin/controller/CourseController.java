/**
 * File: CourseController
 * Author: DorSey Q F TANG
 * Created: 2020/3/29 12:51
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.exception.ApiInvokedException;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(@Qualifier("courseService") final CourseService courseService) {
        this.courseService = courseService;
    }

    @ResponseBody
    @GetMapping("/api/courses/category/{categoryId}")
    public Response<List<Course>> getCoursesByCategoryId(@PathVariable("categoryId") final short categoryId) {
        final List<Course> courses = courseService.getCoursesByCategoryId(categoryId);
        return new Response<List<Course>>().status(Response.SUCCESS).data(courses);
    }

    @ResponseBody
    @GetMapping("/api/courses/category")
    public Response<List<Course>> getCoursesByCategoryName(@RequestParam("name") final String categoryName) {
        try {
            final List<Course> courses = courseService.getCoursesByCategoryName(categoryName);
            return new Response<List<Course>>().status(Response.SUCCESS).data(courses);
        } catch (ApiInvokedException e) {
            log.error("Failed to get courses by category name: {}", categoryName, e);
            return new Response<List<Course>>().status(Response.FAIL);
        }
    }
}
