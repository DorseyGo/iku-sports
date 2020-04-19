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

    @Tolerate
    public IkuSportsConfig() {}

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
        inMemStorage.setToken(token);

        return inMemStorage;
    }
}
