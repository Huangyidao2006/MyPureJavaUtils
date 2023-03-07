package com.hj.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hj at 2022/11/16 10:39
 */
public class RegExpUtil {
    public static Integer parseInt(String s) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            String num = matcher.group();
            return Integer.parseInt(num);
        }

        return null;
    }
}
