import com.hj.utils.DigestUtil;
import com.hj.utils.TimeUtil;
import test.UdpHelperTest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String... args) {
//        UdpHelperTest udpHelperTest = new UdpHelperTest();
//        udpHelperTest.test();

//        long time = 0L;
//
//        while (true) {
//            System.out.println(TimeUtil.toPlayerTimeStamp(time));
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            time += 1000;
//        }

//        System.out.println(DigestUtil.md5("abc123".getBytes(), true));
//        System.out.println(DigestUtil.sha256("abc123".getBytes(), true));

//        long sec = TimeUtil.toSeconds("2018-08-17T04:14:53+08:00");
//        System.out.println("sec=" + sec);
//        System.out.println(TimeUtil.getTime("2018-08-17T04:14:53+08:00"));
//        System.out.println(TimeUtil.getDate("2018-08-17T04:14:53+08:00"));
//        System.out.println(TimeUtil.toISO8601DateTime(System.currentTimeMillis()));
//        System.out.println(TimeUtil.toTimeString(System.currentTimeMillis()));
//        System.out.println(TimeUtil.getTimeZone());

//        long sec = TimeUtil.toSeconds("2022-12-30T20:00:00");
//        System.out.println(sec);
//        System.out.println(new Date(sec * 1000));

        byte[] dataUtf8 = "你好".getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(dataUtf8));

        try {
            byte[] dataGBK = "你好".getBytes("GBK");
            System.out.println(Arrays.toString(dataGBK));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将s中的整数部分转成int，若没有则返回null。
     *
     * @param s
     * @return
     */
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
