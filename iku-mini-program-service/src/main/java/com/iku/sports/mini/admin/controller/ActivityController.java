/**
 * File: ActivityController
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.annotation.WebLog;
import com.iku.sports.mini.admin.entity.Activity;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller, which service the request to <tt>activity</tt> resources.
 */
@RestController
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(@Qualifier("activityService") final ActivityService activityService) {
        this.activityService = activityService;
    }

    @WebLog(response = false)
    @GetMapping("/activities")
    public Response<List<Activity>> getFirst3Activities() {
        final List<Activity> activities = activityService.getFirst3Activities();
        return Response.ok(activities);
    }

    @WebLog(response = false)
    @GetMapping("/activities/category/{categoryId}")
    public Response<List<Activity>> getFirst3ActivitiesByCategoryId(@PathVariable("categoryId") final short categoryId) {
        final List<Activity> activities = activityService.getFirst3ActivitiesByCategoryId(categoryId);
        return Response.ok(activities);
    }
}
