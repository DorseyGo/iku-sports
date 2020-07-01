/**
 * File: GetOpenIdAndSessionKeyResponse
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 15:08
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
public class GetOpenIdAndSessionKeyResponse {

    @JsonProperty("openid")
    private String openId;

    @JsonProperty("session_key")
    private String sessionKey;

    @JsonProperty("unionid")
    private String unionId;

    @JsonProperty("errcode")
    private int errorCode;

    @JsonProperty("errmsg")
    private String errorMessage;
}
