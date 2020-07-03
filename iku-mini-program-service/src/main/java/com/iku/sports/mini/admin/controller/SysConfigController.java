/**
 * File: SysConfigController
 * Author: DorSey Q F TANG
 * Created: 2020/7/3 22:10
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.SysConfig;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.SysConfigService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller, which is used to handle the request for <tt>system configuration</tt>.
 */
@RestController
public class SysConfigController {
    private final SysConfigService sysConfigService;

    public SysConfigController(
            @Qualifier("sysConfigService") final SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

    /**
     * Returns the system configuration, which has the key-value pair, with key specified.
     *
     * @param key the key.
     * @return the system configuration.
     * @throws ApiServiceException if any errors detected during process.
     */
    @RequestMapping("/configs/{configKey}")
    public Response<SysConfig> getSysConfigByKey(final String key) throws ApiServiceException {
        SysConfig sysConfig = sysConfigService.getSysConfigByKey(key);
        return Response.ok(sysConfig);
    }
}
