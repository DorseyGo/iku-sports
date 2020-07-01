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
    private String sessionKey;
    private String name;
    private String avatar;
    private char gender;
    private String telephone;
    private String province;
    private String city;
    private String country;

    @Tolerate
    public User() {
        // empty for initilization.
    }
}
