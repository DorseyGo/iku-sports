/*
 * File: TeachingStyleService
 * Author: DorSey Q F TANG
 * Created: 2020/7/16
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.TeachingStyle;
import com.iku.sports.mini.admin.exception.ApiServiceException;

import java.util.List;

public interface TeachingStyleService {

    List<TeachingStyle> getTeachingStyles() throws ApiServiceException;
}
