package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Favorite;

import java.util.List;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
public interface FavoriteService {
    void addFavorite(int favoriteId, int favoriteType, String userId) throws Exception;

    void delFavorite(int favoriteId, int favoriteType, String userId) throws Exception;

    List<Favorite> getFavoriteByUserId(String userId, int favoriteType) throws Exception;

    Integer getFavoriteSummary(int favoriteId, int favoriteType, String userId) throws Exception;
}