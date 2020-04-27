package com.iku.sports.mini.admin.service.impl;

import com.google.common.base.Strings;
import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.Coach;
import com.iku.sports.mini.admin.entity.Favorite;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.CoachInfo;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.CoachRepository;
import com.iku.sports.mini.admin.service.CoachService;
import com.iku.sports.mini.admin.service.FavoriteService;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * File: Coach
 * Author: Huanghz
 * Created: ${date}
 * Description:
 * CopyRight: All rights reserved
 **/
@Slf4j
@Service("coachService")
public class CoachServiceImpl implements CoachService {
    private final CoachRepository coachRepository;
    private final IkuSportsConfig config;
    private final FavoriteService favoriteService;

    @Autowired
    public CoachServiceImpl(@Qualifier("coachRepository") CoachRepository coachRepository,
            IkuSportsConfig config, FavoriteService favoriteService) {
        this.coachRepository = coachRepository;
        this.config = config;
        this.favoriteService = favoriteService;
    }


    @Override
    public List<CoachInfo> getAllCoachInfos() {
        final List<CoachInfo> coachInfos = coachRepository.getAllCoachesBriefs();
        coachInfos.forEach(coachInfo -> {
            coachInfo.setHeadingImgUrl(Utils.join(config.getStaticResourceServer(), coachInfo.getHeadingImgUrl(),
                    Constants.FORWARD_SLASH));
        });

        return coachInfos;
    }

    @Override
    public Coach getCoachById(int id) throws ApiServiceException {
        try {
            final Coach coach = coachRepository.getCoachById(id);
            coach.setHeadingImgUrl(
                    Utils.join(config.getStaticResourceServer(), coach.getHeadingImgUrl(), Constants.FORWARD_SLASH));

            return coach;
        } catch (DataAccessException e) {
            log.error("Failed to find coach by Id: {}", id, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERROR);
        }
    }

    @Override
    public boolean isCoachFavoritedByUserId(int id, String userId) throws ApiServiceException {
        if (Strings.isNullOrEmpty(userId)) {
            throw new ApiServiceException(IkuSportsError.USER_REQUIRED_ERR);
        }

        return favoriteService.existsFavorite(userId, id, Favorite.FavoriteType.FOR_COACH.getCode());
    }
}
