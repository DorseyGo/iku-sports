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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Builder
@Configuration
@ConfigurationProperties(prefix = "iku.mini-program")
public class IkuSportsConfig {
    private String staticResourceServer;

    @Tolerate
    public IkuSportsConfig() {}
}
