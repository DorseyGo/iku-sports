/**
 * File: Overview
 * Author: DorSey Q F TANG
 * Created: 2020/3/21 23:16
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.model;

import com.iku.sports.mini.admin.entity.Activity;
import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.entity.CourseKind;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Overview {
    private String categoryName;

    private List<Activity> activities;

    private List<CourseKind> courseKinds;

    private List<Course> popularCourses;

    private List<Course> classicCourses;

}
