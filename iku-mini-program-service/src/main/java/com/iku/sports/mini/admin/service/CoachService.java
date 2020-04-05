package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Coach;
import com.iku.sports.mini.admin.model.CoachInfo;

import java.util.List;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/4/5
 * Description:
 * CopyRight: All rights reserved
 **/
public interface CoachService {
    List<CoachInfo> getAllCoachesBriefs();
    Coach getCoachById(final int id);
}
