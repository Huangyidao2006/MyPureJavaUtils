package com.hj.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hj at 2022/8/29 17:59
 * <p>
 * 摘要工具类。
 */
public class DigestUtil {
    private static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                sb.append("0");
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    /**
     * 计算128位md5值。
     *
     * @param data 原始数据
     * @return 长度为32的16进制字符串
     */
    public static String md5(byte[] data) {
        return md5(data, false);
    }

    /**
     * 计算128位md5值。
     *
     * @param data 原始数据
     * @param upperCase 结果是否大写
     * @return 长度为32的16进制字符串
     */
    public static String md5(byte[] data, boolean upperCase) {
        MessageDigest digest;
        String encodestr = "";
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(data);
            encodestr = byte2Hex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return upperCase ? encodestr.toUpperCase() : encodestr;
    }

    /**
     * 计算sha256摘要。
     *
     * @param data 原始数据
     * @return 长度为64的16进制字符串
     */
    public static String sha256(byte[] data) {
        return sha256(data, false);
    }

    /**
     * 计算sha256摘要。
     *
     * @param data 原始数据
     * @param upperCase 结果是否大写
     * @return 长度为64的16进制字符串
     */
    public static String sha256(byte[] data, boolean upperCase) {
        MessageDigest digest;
        String encodestr = "";
        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(data);
            encodestr = byte2Hex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return upperCase ? encodestr.toUpperCase() : encodestr;
    }
}
