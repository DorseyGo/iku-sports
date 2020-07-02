package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.service.CourseAppointmentService;
import com.iku.sports.mini.admin.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CourseAppointmentServiceImpl implements CourseAppointmentService {
    @Autowired
    private OrderService orderService;

    void appointCourse(String uid) {
        // 1. 订单找出用户已购买的 course
        // 2. 课程信息
        // 3. 学完的章节，还剩未学的章节
    }
}
