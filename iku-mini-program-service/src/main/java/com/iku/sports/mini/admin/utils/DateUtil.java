package com.iku.sports.mini.admin.utils;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author henlf
 */
public final class DateUtil {
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:MM";
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

    public static long differHour(Date minuend, Date subtrahend) {
        long minuendTime = minuend.getTime();
        long subtrahendTime = subtrahend.getTime();

        long differMilliseconds = minuendTime - subtrahendTime;

        return differMilliseconds / (60 * 60 * 1000);
    }

    public static Date aheadOfMinutes(long minutes) {
        long nowTime = new Date().getTime();

        long time = minutes * 60 * 1000;
        return new Date(nowTime - time);
    }

    public static String format(Date date, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }
}
