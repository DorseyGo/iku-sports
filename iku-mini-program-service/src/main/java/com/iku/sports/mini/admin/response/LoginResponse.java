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

    @JsonProperty("token")
    private String token;

    @Tolerate
    public LoginResponse() {
        // empty
    }
}
