package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Appointment;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.CourseAppoint;
import com.iku.sports.mini.admin.request.AppointClassRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 课程预约
 */
public interface CourseAppointmentService {
    /**
     * 用户已购课程信息
     * @return
     */
    List<CourseAppoint> courseAppoint(String userId) throws ApiServiceException;

    /**
     * 用户预约课程信息
     * @param userId
     * @param arrangedClassId
     * @return
     */
    Appointment appointedClass(@NotNull @NotEmpty String userId, @NotNull Integer arrangedClassId);

    /**
     * 预约
     */
    void appointment(AppointClassRequest appointClassRequest);

    /**
     * 取消预约
     */
    void cancelAppointment(AppointClassRequest appointClassRequest);

    /**
     * 用户已预约课程数
     * @param userId
     * @return
     */
    int countUserAppointment(String userId);

    /**
     * 用户待预约课程数
     * @param userId
     * @return
     */
    int countUserNotAppoint(String userId);
}
