package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Favorite;
import com.iku.sports.mini.admin.exception.ApiServiceException;

import java.util.List;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
public interface FavoriteService {
    void addFavorite(String token, int favoriteId, int favoriteType) throws ApiServiceException;

    boolean existsFavorite(String userId, int favoriteId, int favoriteType) throws ApiServiceException;
}