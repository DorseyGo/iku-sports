package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/4/5
 * Description:
 * CopyRight: All rights reserved
 **/
@Data
@Builder
public class Coach {
    private int id;
    private String name;
    private String title;
    private String headingImgUrl;
    private String gender;
    private int age;
    private String nationality;
    private int level;
    private String introduce;

    // --------
    // whether favourited by current user.
    // --------
    private boolean favorite;

    @Tolerate
    public Coach() {}
}