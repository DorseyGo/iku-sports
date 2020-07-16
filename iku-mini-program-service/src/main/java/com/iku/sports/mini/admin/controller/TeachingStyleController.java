/*
 * File: TeachingStyleController
 * Author: DorSey Q F TANG
 * Created: 2020/7/16
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;


import com.iku.sports.mini.admin.entity.TeachingStyle;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.TeachingStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeachingStyleController {

    private final TeachingStyleService teachingStyleService;

    @Autowired
    public TeachingStyleController(@Qualifier("teachingStyleService") final TeachingStyleService teachingStyleService) {
        this.teachingStyleService = teachingStyleService;
    }

    @GetMapping("/teaching-styles")
    public Response<List<TeachingStyle>> getTeachingStyles() throws ApiServiceException {
        final List<TeachingStyle> teachingStyles = teachingStyleService.getTeachingStyles();
        return Response.ok(teachingStyles);
    }
}
