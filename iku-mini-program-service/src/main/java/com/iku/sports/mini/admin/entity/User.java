/**
 * File: User
 * Author: DorSey Q F TANG
 * Created: 2020/4/19 14:38
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class User {

    private String id;
    private String openId;
    private String token;
    private String nickName;
    private String avatarUrl;

    @Builder.Default
    private char gender = '0';
    private String province;
    private String city;
    private String country;

    @Tolerate
    public User() {
        // empty
    }
}
