package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.Collect;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.CollectService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
@Controller
@Slf4j
public class CollectController {

    private CollectService collectService;

    public CollectController(@Qualifier("collectService") final CollectService collectService) {
        this.collectService = collectService;
    }


    @ResponseBody
    @GetMapping("/api/collect/add")
    public Response addCollect(@RequestParam("collectId") final int collectId,
                               @RequestParam("collectType") final int collectType,
                               @RequestParam("studentId") final int studentId){
        try{
            collectService.addCollect(collectId,collectType,studentId);
            return new Response().status(Response.SUCCESS);
        }catch (Exception e){
            log.error("Student {} fail to collection. ",studentId,e);
            return new Response().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/collect/del")
    public Response addCollect(@RequestParam("id")final int id){
        try{
            collectService.delCollect(id);
            return new Response().status(Response.SUCCESS);
        }catch (Exception e){
            log.error("Student fail to delete collection by id:{}. ",id,e);
            return new Response().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/collect/getList")
    public Response<List<Collect>> getCollectByStudentId(@RequestParam("studentId") final int studentId,
                                                         @RequestParam("collectType") final int collectType){
        try{
            final List<Collect> collects = collectService.getCollectByStudentId(studentId,collectType);
            return new Response<List<Collect>>().status(Response.SUCCESS).data(collects);
        }catch (Exception e){
            log.error("Fail to get collects by studentId:{}",studentId,e);
            return new Response<List<Collect>>().status(Response.FAIL);
        }
    }


}
