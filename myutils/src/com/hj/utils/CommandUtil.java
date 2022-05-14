package com.hj.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hj at 2022/4/8 23:41
 */
public class CommandUtil {
    public static String exec(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            int code = p.waitFor();

            InputStream ins = (0 == code ? p.getInputStream() : p.getErrorStream());
            if (ins != null) {
                byte[] buffer = new byte[ins.available()];
                int ret = ins.read(buffer);
                ins.close();

                if (ret != -1) {
                    return new String(buffer);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return "";
    }
}
