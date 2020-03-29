/**
 * File: Overview
 * Author: DorSey Q F TANG
 * Created: 2020/3/21 23:16
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iku.sports.mini.admin.entity.Activity;
import com.iku.sports.mini.admin.entity.CourseClass;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class Overview {
    private String categoryName;
    private List<Activity> activities;

    @Builder.Default
    private List<CourseClass> top3Populars = Lists.newArrayList();

    @Builder.Default
    private List<CourseClass> top3Classics = Lists.newArrayList();
    /* level & classes */

    @Builder.Default
    private Map<Short, List<CourseClass>> courseClasses = Maps.newHashMap();

    @Tolerate
    public Overview() {}
}
