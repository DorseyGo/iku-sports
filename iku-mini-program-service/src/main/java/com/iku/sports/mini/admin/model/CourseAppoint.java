package com.iku.sports.mini.admin.model;

import lombok.*;

/**
 * 预约课程信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CourseAppoint {
    private Integer courseId;
    private Integer courseLevel;
    private String courseName;
    private Integer totalClass;
    private Integer studiedClass;
    private Integer coachId;
}
