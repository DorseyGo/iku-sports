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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The controller, which services the request for teaching style resource.
 */
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

    @GetMapping("/teaching-styles/{styleId}")
    public Response<TeachingStyle> getTeachingStyleById(
            @PathVariable("styleId") final int styleId) throws ApiServiceException {
        final TeachingStyle teachingStyle = teachingStyleService.getTeachingStyleById(styleId);
        return Response.ok(teachingStyle);
    }

    @GetMapping("/teaching-styles/promotions/{styleId}")
    public Response<List<TeachingStyle>> getPromotionsById(@PathVariable("styleId") final int styleId) throws
            ApiServiceException {
        final List<TeachingStyle> teachingStyles = teachingStyleService.getTeachingStyles();
        final List<TeachingStyle> promotions = teachingStyles.stream()
                .filter(teachingStyle -> styleId != teachingStyle.getId()).collect(Collectors.toList());

        return Response.ok(promotions);
    }

    @PutMapping("/teaching-styles/{styleId}")
    public Response<String> updateWatches(@PathVariable("styleId") final int styleId,
            @RequestParam("watches") final long watches) throws ApiServiceException {
        teachingStyleService.updateWatchesById(styleId, watches);
        return Response.ok();
    }

}
