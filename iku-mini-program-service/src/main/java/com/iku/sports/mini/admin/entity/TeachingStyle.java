/*
 * File: TeachingStyle
 * Author: DorSey Q F TANG
 * Created: 2020/7/16
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.entity;

import lombok.Data;

@Data
public class TeachingStyle {

    private int id;
    private String title;
    private String cover;
    // the tags
    private String tags;
    // derived from tags.
    private String[] labels;
    // the relative url path
    private String video;

}
