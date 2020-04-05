package com.iku.sports.mini.admin.model;

import com.iku.sports.mini.admin.entity.Coach;
import lombok.Builder;
import lombok.Data;

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
    private Coach coach;
    private int numOfCoachClasses;
}
