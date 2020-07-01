/**
 * File: ActivityServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.service.impl;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.Activity;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.ActivityRepository;
import com.iku.sports.mini.admin.service.ActivityService;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * The default implementation of {@link ActivityServiceImpl}.
 */
@Slf4j
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final IkuSportsConfig config;

    @Autowired
    public ActivityServiceImpl(@Qualifier("activityRepository") final ActivityRepository activityRepository,
            IkuSportsConfig config) {
        this.activityRepository = activityRepository;
        this.config = config;
    }

    @Override
    public List<Activity> getFirst3Activities() {
        try {
            List<Activity> activities = activityRepository.findFirst3ByOrderByCreateTimeDesc();
            activities.forEach(activity -> {
                activity.setImage(Utils.join(config.getStaticResourceServer(),
                                             activity.getImage(), Constants.FORWARD_SLASH));
            });

            return activities;
        } catch (DataAccessException e) {
            log.error("==> Failed to fetch first 3 activities", e);
            return Lists.newArrayListWithExpectedSize(0);
        }
    }

    @Override
    public List<Activity> getFirst3ActivitiesByCategoryId(final short categoryId) {
        try {
            return activityRepository.findFirst3ByCategoryIdOrderByCreateTimeDesc(categoryId);
        } catch (SQLException e) {
            log.error("Failed to fetch first 3 activities by category id: {}", categoryId, e);
            return Lists.newArrayListWithExpectedSize(0);
        }
    }
}
