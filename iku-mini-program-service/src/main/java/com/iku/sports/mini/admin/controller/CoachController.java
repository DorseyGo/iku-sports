package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.annotation.WebLog;
import com.iku.sports.mini.admin.entity.Coach;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.CoachInfo;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.CoachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * File: CoachController
 * Author: Huanghz
 * Created: 2020/4/3
 * Description:
 * CopyRight: All rights reserved
 **/
@Slf4j
@RestController
public class CoachController {
    private final CoachService coachService;

    @Autowired
    public CoachController(@Qualifier("coachService") final CoachService coachService) {
        this.coachService = coachService;
    }

    @WebLog
    @GetMapping("/api/coaches/{coachId}")
    public Response<Coach> getCoachById(@PathVariable("coachId") final int id,
            @RequestParam("token") final String userId) throws ApiServiceException {
        final Coach coach = coachService.getCoachById(id);
        final boolean isFavorited = coachService.isCoachFavoritedByUserId(id, userId);
        coach.setFavorite(isFavorited);

        return Response.ok(coach);
    }

}
