package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;

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
    private String heading_img_url;
    private String gender;
    private int age;
    private String nationality;
    private int level;
    private String introduce;
    //private int courseTotalNum;
}