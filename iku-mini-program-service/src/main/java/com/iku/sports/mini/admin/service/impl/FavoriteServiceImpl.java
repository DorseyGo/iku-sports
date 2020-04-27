package com.iku.sports.mini.admin.service.impl;

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
    public void addFavorite(String token, int favoriteId, int favoriteType) throws ApiServiceException,
            DataAccessException {
        final User user = userService.getUserById(token);
        if (user == null) {
            throw new ApiServiceException(IkuSportsError.REQ_RESOURCE_NOT_FOUND_ERR);
        }

        favoriteRepository.insert(user.getId(), favoriteId, favoriteType);
    }
}