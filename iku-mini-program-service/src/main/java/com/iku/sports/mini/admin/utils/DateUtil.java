package com.iku.sports.mini.admin.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author henlf
 */
public final class DateUtil {
    /**
     * Returns date with the specified number of days subtracted.
     * @param days the days to subtract, may be negative
     * @return
     */
    public static Date aheadOf(long days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime aheadDay = now.minusDays(days);

        return localDateTime2Date(aheadDay);
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
}
