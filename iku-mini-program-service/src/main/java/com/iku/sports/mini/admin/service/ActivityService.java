/**
 * File: ActivityService
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Activity;

import java.util.List;

/**
 * The service, which provides business logic for serving <tt>activity</tt> resource request.
 */
public interface ActivityService {
    List<Activity> getFirst3Activities();

    List<Activity> getFirst3ActivitiesByCategoryId(short categoryId);
}
