/**
 * File: CourseController
 * Author: DorSey Q F TANG
 * Created: 2020/3/29 12:51
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.model.Overview;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(@Qualifier("courseService") final CourseService courseService) {
        this.courseService = courseService;
    }

    @ResponseBody
    @GetMapping("/api/course/overview/category/{categoryId}")
    public Response<Overview> getCourseOverviewByCategoryId(@PathVariable("categoryId") final short categoryId) {
        try {
            Overview overview = courseService.getCourseOverviewByCategoryId(categoryId);
            return new Response<Overview>().status(Response.SUCCESS).data(overview);
        } catch (Exception e) {
            log.error("Failed to fetch course overview by category id: {}", categoryId, e);
            return new Response<Overview>().status(Response.FAIL);
        }
    }
}
