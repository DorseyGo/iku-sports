/**
 * File: ActivityServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Activity;
import com.iku.sports.mini.admin.repository.ActivityRepository;
import com.iku.sports.mini.admin.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(@Qualifier("activityRepository") final ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> getFirst3Activities() {
        return activityRepository.findFirst3ByOrderByCreateTimeDesc();
    }

    @Override
    public List<Activity> getFirst3ActivitiesByCategoryId(final short categoryId) {
        return activityRepository.findFirst3ByCategoryIdOrderByCreateTimeDesc(categoryId);
    }
}
