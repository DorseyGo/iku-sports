package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.User;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.repository.FavoriteRepository;
import com.iku.sports.mini.admin.service.FavoriteService;
import com.iku.sports.mini.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    // @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    public void addFavorite(String token, int favoriteId, int favoriteType) throws ApiServiceException {
        try {
            final User user = userService.getUserById(token);
            favoriteRepository.insert(user.getId(), favoriteId, favoriteType);
        } catch (DataAccessException e) {
            log.error("Fail to add favorite, user id {}, favoriteId {}, favoriteType {}", token, favoriteId, favoriteType);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERROR);
        }

    }
}