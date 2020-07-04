/**
 * File: SysConfigServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/7/3 22:17
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.SysConfig;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.repository.SysConfigRepository;
import com.iku.sports.mini.admin.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigRepository sysConfigRepository;
    private final LoadingCache<String, String> cache;
    private final IkuSportsConfig config;

    @Autowired
    public SysConfigServiceImpl(@Qualifier("sysConfigRepository") final SysConfigRepository sysConfigRepository,
            final IkuSportsConfig config) {
        this.sysConfigRepository = sysConfigRepository;
        this.config = config;
        this.cache = CacheBuilder.newBuilder().expireAfterAccess(config.getExpiryInDays(), TimeUnit.DAYS)
                .expireAfterWrite(config.getExpiryInDays(), TimeUnit.DAYS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(final String key) throws Exception {
                        return createValue(key);
                    }
                });
    }

    private String createValue(final String key) {
        final SysConfig sysConfig = sysConfigRepository.findSysConfigByKey(key);
        return (sysConfig != null ? sysConfig.getValue() : null);
    }

    @Override
    public SysConfig getSysConfigByKey(final String key) throws ApiServiceException {
        if (Strings.isNullOrEmpty(key)) {
            log.error("==> Parameter key not specified");
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }

        try {
            String value = cache.get(key);
            return new SysConfig(key, value);
        } catch (ExecutionException e) {
            log.error("==> Failed to load system configuration by key: {}", key, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }
}
