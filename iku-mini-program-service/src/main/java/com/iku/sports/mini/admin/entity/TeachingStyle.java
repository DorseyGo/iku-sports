/*
 * File: TeachingStyle
 * Author: DorSey Q F TANG
 * Created: 2020/7/16
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.entity;

import com.iku.sports.mini.admin.model.SingleHolder;
import lombok.Data;

@Data
public class TeachingStyle {

    private int id;
    private String title;
    private String cover;
    // the tags
    private String tags;
    // derived from tags.
    private SingleHolder[] labels;
    // the relative url path
    private String video;
    private long watches;

}
