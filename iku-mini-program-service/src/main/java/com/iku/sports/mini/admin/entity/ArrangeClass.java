package com.iku.sports.mini.admin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ArrangeClass implements Serializable {
    private Integer id;
    private Integer classId;
    private Integer coachId;
    private Short courseId;
    /**
     * 上课地点
     */
    private String site;
    /**
     * 上课时间
     */
    private Date beginTime;
    /**
     * 下课时间
     */
    private Date endTime;
    /**
     * 课程时长
     */
    private Integer duration;
    /**
     * 课程总人数
     */
    private Integer headcount;
    /**
     * 预约人数
     */
    private Integer appointCount;
    private Date createdTime;

    /**
     * 表示用户是否已预约课程
     */
    private Boolean appointed = false;

    /**
     * 课程第几课时
     */
    private short chapter;
    /**
     * 课程内容
     */
    private String content;
    /**
     * 教练名
     */
    private String name;

}
