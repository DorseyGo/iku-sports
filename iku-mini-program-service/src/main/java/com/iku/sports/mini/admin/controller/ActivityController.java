/**
 * File: ActivityController
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.Activity;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(@Qualifier("activityService") final ActivityService activityService) {
        this.activityService = activityService;
    }

    @ResponseBody
    @GetMapping("/api/activities")
    public Response<Activity> getFirst3Activities() {
        final List<Activity> activities = activityService.getFirst3Activities();
        return new Response<Activity>().status(Response.SUCCESS).data(activities);
    }
}
