/**
 * File: Activity
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class Activity {

    private int id;
    private String image;
    private String link;
    private Date createTime;

    @Tolerate
    public Activity() {}
}
