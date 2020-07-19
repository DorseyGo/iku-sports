package com.iku.sports.mini.admin.job;

import com.iku.sports.mini.admin.service.CourseAppointmentService;
import com.iku.sports.mini.admin.service.MessageNotifyService;
import com.iku.sports.mini.admin.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppointmentTask {
    @Value("iku.mini-program.appoint-ahead-minutes-confirm：30")
    private String aheadMinutesConfirmAppoint;

    private CourseAppointmentService courseAppointmentService;
    private MessageNotifyService messageNotifyService;

    @Autowired
    public AppointmentTask(CourseAppointmentService courseAppointmentService,
                           MessageNotifyService messageNotifyService) {
        this.courseAppointmentService = courseAppointmentService;
        this.messageNotifyService = messageNotifyService;
    }

    /**
     * 每隔 30 分钟确定用户上课
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void attendClass() {
        try {
            courseAppointmentService.userAttendClass(DateUtil.aheadOfMinutes(Long.parseLong(aheadMinutesConfirmAppoint)));
        } catch (Exception e) {
            log.error("occurs error update user attend class", e);
        }
    }

    /**
     * 每隔 1 小时确定用户完成课程
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void completedClass() {
        try {
            courseAppointmentService.completedClass();
        } catch (Exception e) {
            log.error("occurs error update user completed class", e);
        }
    }

    /**
     * 通知用户上课，只通知距上课还有三个小时的用户
     */
    //@Scheduled(cron = "0 0 0/1 * * ?")
    public void notifyAttendClass() {
        try {
            // TODO
        } catch (Exception e) {
            log.error("occurs error notify user attend class", e);
        }
    }
}
