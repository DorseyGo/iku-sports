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

@Data
@Builder
public class Course {
    private short id;

    /**
     * 1, for junior
     * 2, for intermediate
     * 3, for senior
     */
    private char level;
    private long fee;

    @Tolerate
    public Course() {}
}