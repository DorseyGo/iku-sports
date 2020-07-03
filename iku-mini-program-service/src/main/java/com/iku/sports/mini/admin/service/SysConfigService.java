/**
 * File: SysConfigService
 * Author: DorSey Q F TANG
 * Created: 2020/7/3 22:11
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.SysConfig;
import com.iku.sports.mini.admin.exception.ApiServiceException;

/**
 * The service interface, which handles the business logic of resource <tt>system configuration</tt>.
 */
public interface SysConfigService {

    /**
     * Returns the system configuration which is retrieved by the given key.
     *
     * @param key the key.
     * @return the corresponding system configuration.
     * @throws ApiServiceException if any errors detected during process.
     */
    SysConfig getSysConfigByKey(final String key) throws ApiServiceException;
}
