package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.model.CourseAppoint;

import java.util.List;

/**
 * 课程预约
 */
public interface CourseAppointmentService {
    /**
     * 用户已购课程信息
     * @return
     */
    List<CourseAppoint> purchasedCourse(String userId);
}
