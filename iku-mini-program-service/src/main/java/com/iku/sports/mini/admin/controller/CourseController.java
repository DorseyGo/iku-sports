/**
 * File: CourseController
 * Author: DorSey Q F TANG
 * Created: 2020/3/21 23:10
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.model.Overview;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(@Qualifier("courseService") final CourseService courseService) {
        this.courseService = courseService;
    }

    @ResponseBody
    @GetMapping("/api/course/overview/{category_id}")
    public Response<Overview> getCourseOverviewByCategoryId(@PathVariable("category_id") final short categoryId) {
        final Overview overview = courseService.getCourseOverviewByCategoryId(categoryId);

        return new Response<Overview>().status(Response.SUCCESS).data(overview);
    }
}
