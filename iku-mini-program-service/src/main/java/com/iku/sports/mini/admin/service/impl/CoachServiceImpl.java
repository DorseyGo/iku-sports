package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.Coach;
import com.iku.sports.mini.admin.model.CoachInfo;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.CoachRepository;
import com.iku.sports.mini.admin.service.CoachService;
import com.iku.sports.mini.admin.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * File: Coach
 * Author: Huanghz
 * Created: ${date}
 * Description:
 * CopyRight: All rights reserved
 **/
@Service("coachService")
public class CoachServiceImpl implements CoachService {
    private final CoachRepository coachRepository;
    private final IkuSportsConfig config;

    @Autowired
    public CoachServiceImpl(@Qualifier("coachRepository") CoachRepository coachRepository,
            IkuSportsConfig config) {
        this.coachRepository = coachRepository;
        this.config = config;
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
    public Coach getCoachById(int id) {
        return coachRepository.getCoachById(id);
    }
}
