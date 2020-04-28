/*
 * File: WatchedClassHisServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/4/28
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.repository.WatchedClassHisRepository;
import com.iku.sports.mini.admin.service.WatchedClassHisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service("watchedClassHisService")
public class WatchedClassHisServiceImpl implements WatchedClassHisService {

    private final WatchedClassHisRepository watchedClassHisRepository;

    @Autowired
    public WatchedClassHisServiceImpl(
            @Qualifier("watchedClassHisRepository") WatchedClassHisRepository watchedClassHisRepository) {
        this.watchedClassHisRepository = watchedClassHisRepository;
    }

    @Override
    public boolean existsWatchedHis(String userId, int classId) throws ApiServiceException {
        try {
            return (watchedClassHisRepository.countByUserIdAndClassId(userId, classId) > 0);
        } catch (DataAccessException e) {
            log.error("Failed to count the history via user id: {} and class id: {}", userId, classId, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERROR);
        }
    }
}
