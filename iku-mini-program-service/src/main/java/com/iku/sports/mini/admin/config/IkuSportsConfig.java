/**
 * File: IkuSportsConfig
 * Author: DorSey Q F TANG
 * Created: 2020/4/5 10:59
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.config;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Builder
@Configuration
@ConfigurationProperties(prefix = "iku.mini-program")
public class IkuSportsConfig {
    private String staticResourceServer;
    private String appId;
    private String secret;
    private String token;
    private String salt;
    private String mchId;
    @Builder.Default
    private long expiryInDays = 7;
    private String key;

    @Builder.Default
    private int pageSize = 10;

    /**
     * ip address that can be accessed by external app
     */
    private String machineIpAddr;
    private String notifyUrlAddr;
    private String wxApiKey;

    @Tolerate
    public IkuSportsConfig() {
        this.pageSize = 10;
        this.expiryInDays = 7;
    }

    @Bean
    public WxMpService wxMpService() {
        final WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage());

        return wxMpService;
    }

    public WxMpConfigStorage wxMpInMemoryConfigStorage() {
        final WxMpInMemoryConfigStorage inMemStorage = new WxMpInMemoryConfigStorage();
        inMemStorage.setSecret(secret);
        inMemStorage.setAppId(appId);
        // no need token under this circumstance.
//        inMemStorage.setToken(token);

        return inMemStorage;
    }
}
