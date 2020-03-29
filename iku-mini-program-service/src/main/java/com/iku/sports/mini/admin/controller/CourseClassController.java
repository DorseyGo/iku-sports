package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.CourseClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * File: ClassController
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:class management
 * CopyRight: All rights reserved
 **/
@Slf4j
@Controller
public class CourseClassController {

    private final CourseClassService courseClassService;

    @Autowired
    public CourseClassController(@Qualifier("courseClassService") final CourseClassService courseClassService) {
        this.courseClassService = courseClassService;
    }

    @ResponseBody
    @GetMapping("/api/classes")
    public Response<List<CourseClass>> getAllClasses() throws Exception {
        try {
            final List<CourseClass> courseClasses = courseClassService.getAllClasses();
            return new Response<List<CourseClass>>().status(Response.SUCCESS).data(courseClasses);
        } catch (Exception e) {
            log.error("Failed to get all classes", e);
            return new Response<List<CourseClass>>().status(Response.FAIL);
        }
    }



}
