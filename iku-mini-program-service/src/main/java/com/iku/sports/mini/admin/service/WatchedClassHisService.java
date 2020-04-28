/*
 * File: WatchedClassHisService
 * Author: DorSey Q F TANG
 * Created: 2020/4/28
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.exception.ApiServiceException;

public interface WatchedClassHisService {
    boolean existsWatchedHis(String userId, int classId) throws ApiServiceException;
}
