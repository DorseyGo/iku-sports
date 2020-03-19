/**
 * File: ActivityService
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Activity;

import java.util.List;

public interface ActivityService {
    List<Activity> getFirst3Activities();
}
