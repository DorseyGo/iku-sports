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
    /**
     * 课程 id
     */
    private Integer courseId;
    /**
     * 课程级别
     */
    private Character courseLevel;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课程标签，即课程核心要素
     */
    private String courseDesc;
    /**
     * 总课时
     */
    private Integer totalClass;
    /**
     * 已学课时
     */
    private Integer studiedClass;

}
