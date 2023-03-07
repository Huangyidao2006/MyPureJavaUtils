package com.hj.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Locale;

/**
 * Created by hj at 2022/4/10 23:07
 */
public class TimeUtil {
    public static String toTimeString(long timeMs) {
        return toTimeString(timeMs, "yyyy-MM-dd HH:mm:ss");
    }

    public static String toPlayerTimeStamp(long timeMs) {
        String timeStr = "";
        long timeSec = timeMs / 1000;

        int sec = (int) (timeSec % 60);
        long timeMin = timeSec / 60;
        int min = (int) (timeMin % 60);
        long timeHour = timeMin / 60;

        if (timeHour != 0) {
            timeStr = String.format(Locale.CHINA,"%02d:%02d:%02d", timeHour, min, sec);
        } else {
            timeStr = String.format(Locale.CHINA,"%02d:%02d", min, sec);
        }

        return timeStr;
    }

    public static String toTimeString(long timeMs, String fmt) {
        return DateTimeFormat.forPattern(fmt).print(timeMs);
    }

    public static String getTimeZone() {
        DateTime dt = new DateTime();
        return ISODateTimeFormat.dateTimeNoMillis().print(dt).substring(19);
    }

    public static long toSeconds(String timeISO8601) {
        DateTime dateTime = DateTime.parse(timeISO8601);
        return dateTime.getMillis() / 1000;
    }

    public static String toISO8601DateTime(long seconds) {
        return ISODateTimeFormat.dateTimeNoMillis().print(seconds);
    }

    public static String getTime(String timeISO8601) {
        DateTime dateTime = DateTime.parse(timeISO8601);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");

        return fmt.print(dateTime);
    }

    public static String getDate(String timeISO8601) {
        DateTime dateTime = DateTime.parse(timeISO8601);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy年MM月dd日");

        return fmt.print(dateTime);
    }

    public static String genFileNameByTime() {
        return toTimeString(System.currentTimeMillis(), "yyyy_MM_dd_HH_mm_ss");
    }
}
