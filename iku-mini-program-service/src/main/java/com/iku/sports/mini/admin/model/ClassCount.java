package com.iku.sports.mini.admin.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/04/11
 * Description:
 * CopyRight: All rights reserved
 **/
@Data
@Builder
public class ClassCount {
    int totalCnt;
    double chapter;

    @Tolerate
    public ClassCount(){}
}
