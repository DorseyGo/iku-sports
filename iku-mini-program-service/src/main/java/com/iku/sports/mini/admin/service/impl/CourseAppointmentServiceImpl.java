package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.model.CourseAppoint;
import com.iku.sports.mini.admin.service.CourseAppointmentService;
import com.iku.sports.mini.admin.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CourseAppointmentServiceImpl implements CourseAppointmentService {
    @Autowired
    private OrderService orderService;

    @Override
    public List<CourseAppoint> purchasedCourse(String userId) {
        // TODO
        return null;
    }
}
