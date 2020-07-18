package com.iku.sports.mini.admin.job;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AppointmentTask {
    @Value("iku.mini-program.appoint-ahead-minutes-confirm")
    private String aheadMinutesConfirmAppoint;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void attendClass() {
        // TODO
    }
}
