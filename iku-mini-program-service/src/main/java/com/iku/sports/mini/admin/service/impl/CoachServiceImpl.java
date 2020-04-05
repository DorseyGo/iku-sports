package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Coach;
import com.iku.sports.mini.admin.model.CoachInfo;
import com.iku.sports.mini.admin.repository.CoachRepository;
import com.iku.sports.mini.admin.service.CoachService;
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

    @Autowired
    public CoachServiceImpl(@Qualifier("coachRepository") CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }


    @Override
    public List<CoachInfo> getAllCoachesBriefs(){
        return coachRepository.getAllCoachesBriefs();
    }

    @Override
    public Coach getCoachById(int id) {
        return coachRepository.getCoachById(id);
    }
}
