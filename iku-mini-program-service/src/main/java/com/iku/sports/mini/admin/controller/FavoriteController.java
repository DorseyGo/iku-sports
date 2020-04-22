package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.Favorite;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.iku.sports.mini.admin.request.FavoriteRequest;

import java.util.List;

/**
 * File: FavoriteController
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
@Controller
@Slf4j
public class FavoriteController {

    private FavoriteService favoriteService;

    public FavoriteController(@Qualifier("favoriteService") final FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }


    @ResponseBody
    @PostMapping("/api/favorite/add")
    public Response addFavorite(@RequestBody FavoriteRequest favoriteRequest){
        try{
            favoriteService.addFavorite(favoriteRequest.getFavoriteId(), favoriteRequest.getFavoriteType(), favoriteRequest.getUserId());
            return new Response().status(Response.SUCCESS);
        }catch (Exception e){
            log.error("Student {} fail to collection. ",favoriteRequest.getUserId(),e);
            return new Response().status(Response.FAIL);
        }
    }

    @ResponseBody
    @PostMapping("/api/favorite/del")
    public Response delFavorite(@RequestBody FavoriteRequest favoriteRequest){
        try{
            favoriteService.delFavorite(favoriteRequest.getFavoriteId(), favoriteRequest.getFavoriteType(), favoriteRequest.getUserId());
            return new Response().status(Response.SUCCESS);
        }catch (Exception e){
            log.error("Student fail to delete collection. ",e);
            return new Response().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/favorite/getList")
    public Response<List<Favorite>> getFavoriteByUserId(@RequestParam("userId") final String user_id,
                                                          @RequestParam("favoriteType") final int favoriteType){
        try{
            final List<Favorite> favorites = favoriteService.getFavoriteByUserId(user_id,favoriteType);
            return new Response<List<Favorite>>().status(Response.SUCCESS).data(favorites);
        }catch (Exception e){
            log.error("Fail to get collects by user_id:{}",user_id,e);
            return new Response<List<Favorite>>().status(Response.FAIL);
        }
    }

    @ResponseBody
    @PostMapping("/api/favorite/summary")
    public Response<Integer> getFavoriteSummary(@RequestBody FavoriteRequest favoriteRequest){
        try{
            final Integer totalRecord = favoriteService.getFavoriteSummary(favoriteRequest.getFavoriteId(), favoriteRequest.getFavoriteType(), favoriteRequest.getUserId());
            return new Response<Integer>().status(Response.SUCCESS).data(totalRecord);
        }catch (Exception e){
            log.error("Fail to get total collection by user_id:{}",favoriteRequest.getUserId(),e);
            return new Response<Integer>().status(Response.FAIL);
        }
    }


}