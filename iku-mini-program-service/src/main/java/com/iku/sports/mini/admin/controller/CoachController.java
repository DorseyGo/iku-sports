package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.Coach;
import com.iku.sports.mini.admin.model.CoachInfo;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.CoachService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * File: CoachController
 * Author: Huanghz
 * Created: 2020/4/3
 * Description:
 * CopyRight: All rights reserved
 **/
@Slf4j
@Controller
public class CoachController  {
    private  final CoachService coachService;
    private Logger logger = LoggerFactory.getLogger(CoachController.class);

    @Autowired
    public CoachController(@Qualifier("coachService") final CoachService coachService){
        this.coachService = coachService;
    }

    @ResponseBody
    @GetMapping("/api/coaches/briefs")
    public Response<List<CoachInfo>> getAllCoachesBriefs() {
        try {
            final List<CoachInfo> coachInfos = coachService.getAllCoachesBriefs();
            return new Response<List<CoachInfo>>().status(Response.SUCCESS).data(coachInfos);
        } catch (Exception e) {
            logger.error("Fail to get about of all coaches.", e);
            return new Response<List<CoachInfo>>().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/coach/{coachid}")
    public Response<Coach> getCoachById(@PathVariable("coachid")final int id){
        try{
            final Coach coach = coachService.getCoachById(id);
            return new Response<Coach>().status(Response.SUCCESS).data(coach);
        }catch (Exception e){
            logger.error("Fail to get coach by id:{}",id,e);
            return new Response<Coach>().status(Response.FAIL);
        }
    }


}
