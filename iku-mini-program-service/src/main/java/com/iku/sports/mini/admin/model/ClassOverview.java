/**
 * File: ClassOverview
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
public class ClassOverview {
    private int classId;
    private String classTitle;
    private int chapter;
    private String videoUrl;
    private String content;
    private int numSeries;
    private String coachName;

    // ----
    private int nextClassId;
    private String nextClassTitle;
    private int nextChapter;

    @Tolerate
    public ClassOverview() {}
}
