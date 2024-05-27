package com.example.assets.business.service;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/21 14:50
 **/

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTestUtil {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String str="20110823";
        Date dt=sdf.parse(str);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR,-1);//日期减1年
        rightNow.add(Calendar.MONTH,3);//日期加3个月
        rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
        Date dt1=rightNow.getTime();
        String reStr = sdf.format(dt1);
        System.out.println(reStr);

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        str="2024-1-16 16:43:52.997";
        dt = new Date();
        str= sdf.format(dt);
//        System.out.println(str.substring(0, str.length() - 4));
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt2 = sdf2.parse(str);
//        Date dt2 = sdf2.parse(str.substring(0, str.length() - 4));
        System.out.println(sdf.format(dt) + " --> " +  sdf.format(dt2));

        long timestamp = dt2.getTime();

        // 将时间戳转换为Instant对象，并通过ZoneId将其调整到特定时区（这里假设是系统默认时区）
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        // 格式化为字符串
        String formattedDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(dateTime);
//        System.out.println(formattedDateTime);

        System.out.println(sdf.format(dt) + " --> " +  formattedDateTime);

        // 获取当前时间的Instant对象，这相当于Unix纪元开始的时间戳
        Instant now = Instant.now();

        // 转换为秒（epochSecond属性直接提供秒值）
        long secondsTimestamp = now.getEpochSecond();

        System.out.println("Seconds since epoch: " + secondsTimestamp);

    }

}

//    Tue Jan 16 16:40:52 CST 2024 --> 2024-01-16 16:40:52.997
//        Seconds since epoch: 1705395162