package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Favorite;
import com.iku.sports.mini.admin.repository.FavoriteRepository;
import com.iku.sports.mini.admin.service.FavoriteService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private Integer totalNum;

    public FavoriteServiceImpl(@Qualifier("favoriteRepository") FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void addFavorite(int favoriteId, int favoriteType, String userId) throws Exception {
        favoriteRepository.addFavorite(favoriteId,favoriteType,userId);
    }

    @Override
    public void delFavorite(int favoriteId, int favoriteType, String userId) throws Exception {
        favoriteRepository.delFavorite(favoriteId, favoriteType, userId);
    }

    @Override
    public List<Favorite> getFavoriteByUserId(String userId, int favoriteType) throws Exception {
        if(favoriteType == 0 ){
            return favoriteRepository.getFavoriteByStudentId(userId);
        }
        return favoriteRepository.getFavoriteByStudentIdFavoriteType(userId,favoriteType);
    }

    @Override
    public Integer getFavoriteSummary(int favoriteId, int favoriteType, String userId) throws Exception {
        totalNum = favoriteRepository.getFavoriteSummary(favoriteId, favoriteType, userId);
        if(totalNum == null ){
            return 0;
        }else{
            return totalNum;
        }
    }
}