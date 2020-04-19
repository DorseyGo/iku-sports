/**
 * File: JSCode2SessionParams
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 14:47
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.request;

import com.iku.sports.mini.admin.annotation.Params;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class JSCode2SessionParams implements QueryParams {

    @Params(key = "appid")
    private String appId;

    @Params(key = "secret")
    private String secret;

    @Params(key = "js_code")
    private String jsCode;

    @Params(key = "grant_type")
    private String grantType;

    @Tolerate
    public JSCode2SessionParams() {
        // empty
    }
}
