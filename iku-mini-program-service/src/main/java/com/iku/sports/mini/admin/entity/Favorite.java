package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
@Data
@Builder
public class Favorite {

    private int id;
    private int studentId;
    private int collectId;
    private int collectType;

    @Tolerate
    public Favorite(){};
}