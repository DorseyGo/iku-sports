package com.iku.sports.mini.admin.entity;

import com.iku.sports.mini.admin.model.Constants;
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
     * 预约课程状态
     * @see Constants.ClassAppointStatus
     */
    private Integer status;

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
