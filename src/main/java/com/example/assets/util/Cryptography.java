package com.example.assets.util;


import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/04/13 15:42
 **/
public class Cryptography {

//    /**
//     * MD5加密之方法二
//     * @explain java实现
//     * @param str
//     *            待加密字符串
//     * @return 16进制加密字符串
//     */
//    public static String encrypt2ToMD5(String str) {
//        // 加密后的16进制字符串
//        String hexStr = "";
//        try {
//            // 此 MessageDigest 类为应用程序提供信息摘要算法的功能
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            // 转换为MD5码
//            byte[] digest = md5.digest(str.getBytes("utf-8"));
//            hexStr = ByteUtils.toHexString(digest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return hexStr;
//    }　　

    /**
     * MD5加密之方法一
     * @explain 借助apache工具类DigestUtils实现
     * @param str
     *            待加密字符串
     * @return 16进制加密字符串
     */
    public static String encryptToMD5(String str) {
        return DigestUtils.md5Hex(str);
    }


    public static String getMD5Three(String path) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi.toString(16);
    }
}
