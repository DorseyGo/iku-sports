package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Appointment;
import com.iku.sports.mini.admin.entity.ArrangeClass;
import com.iku.sports.mini.admin.repository.ArrangeClassRepository;
import com.iku.sports.mini.admin.service.ArrangeClassService;
import com.iku.sports.mini.admin.service.CourseAppointmentService;
import com.iku.sports.mini.admin.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author henlf
 */
@Service
@Slf4j
public class ArrangeClassServiceImpl implements ArrangeClassService {
    @Value("${iku.mini-program.appoint_ahead_day:0}")
    private Integer aheadDays;

    private ArrangeClassRepository arrangeClassRepository;
    private CourseAppointmentService courseAppointmentService;

    @Autowired
    public ArrangeClassServiceImpl(ArrangeClassRepository arrangeClassRepository,
                                   CourseAppointmentService courseAppointmentService) {
        this.arrangeClassRepository = arrangeClassRepository;
        this.courseAppointmentService = courseAppointmentService;
    }

    @Override
    public List<ArrangeClass> courseArrange(short courseId, String userId) {
        // 查找课程的课程安排，且上课时间大于指定的时间
        List<ArrangeClass> arrangedClasses = arrangeClassRepository.findArrangeClassByCourseId(courseId, DateUtil.aheadOf(aheadDays));
        if (CollectionUtils.isEmpty(arrangedClasses)) {
            log.warn("no class arranged for course:[{}]", courseId);
            return Collections.emptyList();
        }

        // 已经预约的课程安排
        arrangedClasses.forEach(arrangeClass -> {
            Appointment appointment = courseAppointmentService.appointedClass(userId, arrangeClass.getId());
            if (null != appointment) {
                arrangeClass.setStatus(appointment.getStatus());
            }
        });

        return arrangedClasses;
    }
}
