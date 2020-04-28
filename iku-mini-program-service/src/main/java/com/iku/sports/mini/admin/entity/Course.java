/**
 * File: Course
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 14:58
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.math.BigDecimal;

@Data
@Builder
public class Course {
    private short id;
    private String name;
    /**
     * 1, for junior
     * 2, for intermediate
     * 3, for senior
     */
    private char level;
    private long fee;
    private long joiner;
    /* the background image url address */
    private String backgroundImg;
    private String categoryName;

    /**
     * The charge, which derived from field 'fee'
     */
    private BigDecimal charge;

    @Tolerate
    public Course() {}
}
