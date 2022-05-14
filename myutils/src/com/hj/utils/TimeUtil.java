package com.hj.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hj at 2022/4/10 23:07
 */
public class TimeUtil {
    public static String toTimeString(long timeMs) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                .format(new Date(timeMs));
    }
}
