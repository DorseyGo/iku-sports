package com.iku.sports.mini.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author henlf
 */
@Data
public class Appointment {
    private Integer id;
    /**
     * 课程计划表 id
     */
    private Integer arrangedClassId;
    private String userId;
    private Integer status;
    private Date updateTime;
    private Date createTime;
}
