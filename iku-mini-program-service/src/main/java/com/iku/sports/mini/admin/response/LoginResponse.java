/**
 * File: LoginResponse
 * Author: DorSey Q F TANG
 * Created: 2020/4/19 14:23
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class LoginResponse {

    @JsonProperty("session_key")
    private String userId;

    @Tolerate
    public LoginResponse() {
        // empty
    }
}
