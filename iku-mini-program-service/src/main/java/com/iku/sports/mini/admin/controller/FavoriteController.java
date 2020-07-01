package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.annotation.WebLog;
import com.iku.sports.mini.admin.entity.Favorite;
import com.iku.sports.mini.admin.exception.ApiServiceException;
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
@RestController
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(@Qualifier("favoriteService") final FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @WebLog
    @PostMapping("/api/favorite")
    public Response<String> addFavorite(@RequestBody FavoriteRequest favoriteRequest) throws ApiServiceException {
        favoriteService.addFavorite(favoriteRequest.getUserId(), favoriteRequest.getFavoriteId(),
                favoriteRequest.getFavoriteType());

        return Response.ok();
    }

    @WebLog
    @GetMapping("/api/favorite")
    public Response<Boolean> existsFavorite(@RequestParam("token") final String userId, @RequestParam("favoriteId") final int favoriteId,
            @RequestParam("favoriteType") final int favoriteType) throws ApiServiceException {
        boolean exists = favoriteService.existsFavorite(userId, favoriteId, favoriteType);
        return Response.ok(exists);
    }
}