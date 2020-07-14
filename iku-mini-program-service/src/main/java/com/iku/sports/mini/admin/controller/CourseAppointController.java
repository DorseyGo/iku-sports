package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.annotation.WebLog;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.CourseAppoint;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.request.AppointClassRequest;
import com.iku.sports.mini.admin.service.CourseAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class CourseAppointController {
    private CourseAppointmentService courseAppointmentService;

    @Autowired
    public CourseAppointController(CourseAppointmentService courseAppointmentService) {
        this.courseAppointmentService = courseAppointmentService;
    }

    /**
     * return a sort of course information that can be make reservation
     * @param userId unique id for user
     * @return
     */
    @WebLog(response = false)
    @GetMapping("appoint/course/list/{userId}")
    public Response<List<CourseAppoint>> appointCourse(@NotNull(message = "userId cannot be null")
                                                       @NotEmpty(message = "userId cannot be empty")
                                                       @PathVariable("userId") String userId) throws ApiServiceException {
        List<CourseAppoint> appointCourses = courseAppointmentService.courseAppoint(userId);
        return Response.ok(appointCourses);
    }

    @WebLog(response = false)
    @PostMapping("appoint/course/class")
    public Response<Void> appointmentClass(@Valid @RequestBody AppointClassRequest appointClassRequest) {
        courseAppointmentService.appointment(appointClassRequest);
        return Response.ok();
    }
}
