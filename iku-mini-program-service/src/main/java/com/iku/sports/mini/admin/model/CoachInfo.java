package com.iku.sports.mini.admin.model;

import com.iku.sports.mini.admin.entity.Coach;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * File: Coach
 * Author: Huanghz
 * Created: ${date}
 * Description:
 * CopyRight: All rights reserved
 **/
@Data
@Builder
public class CoachInfo {
    private int id;
    private String name;
    private String title;
    private String headingImgUrl;
    private String gender;
    private String nationality;
    private int level;
    private String introduce;

    private int numOfCoachClasses;

    @Tolerate
    public CoachInfo() {}
}
