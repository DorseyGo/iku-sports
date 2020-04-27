package com.iku.sports.mini.admin.service.impl;

import com.google.common.base.Strings;
import com.iku.sports.mini.admin.entity.User;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.repository.FavoriteRepository;
import com.iku.sports.mini.admin.service.FavoriteService;
import com.iku.sports.mini.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
@Transactional
@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;

    @Autowired
    public FavoriteServiceImpl(
            @Qualifier("favoriteRepository") FavoriteRepository favoriteRepository,
            @Qualifier("userService") UserService userService) {
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
    }


    @Override
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    public void addFavorite(String userId, int favoriteId, int favoriteType) throws ApiServiceException,
            DataAccessException {
        if (Strings.isNullOrEmpty(userId)) {
            throw new ApiServiceException(IkuSportsError.USER_REQUIRED_ERR);
        }

        favoriteRepository.insert(userId, favoriteId, favoriteType);
    }

    @Override
    public boolean existsFavorite(String userId, int favoriteId, int favoriteType) throws ApiServiceException {
        return (favoriteRepository.countFavorites(userId, favoriteId, favoriteType) > 0);
    }
}