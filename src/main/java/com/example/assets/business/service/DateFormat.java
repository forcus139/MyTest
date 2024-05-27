//package com.example.assets.business.service;
//
///**
// * TODO
// *
// * @author GengTianMing
// * @since 2023/02/21 14:49
// **/
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.TimeZone;
//
//public class DateFormat {
//
//    /**
//     * 日期减几年
//     */
//    public static String dateMinusYear(String str) throws Exception {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//        Date dt = sdf.parse(str);
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(dt);
//        rightNow.add(Calendar.YEAR, -1);// 日期减1年
//        Date dt1 = rightNow.getTime();
//        String reStr = sdf.format(dt1);
//        return reStr;
//    }
//
//    /**
//     * 日期加几年
//     */
//    public static String dateAddYear(String str) throws Exception {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//        Date dt = sdf.parse(str);
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(dt);
//        rightNow.add(Calendar.YEAR, 1);// 日期加1年
//        Date dt1 = rightNow.getTime();
//        String reStr = sdf.format(dt1);
//        return reStr;
//    }
//
//    /**
//     * 日期减几月
//     */
//    public static String dateMinusMonth(String str) throws Exception {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//        Date dt = sdf.parse(str);//将字符串生成Date
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(dt);//使用给定的 Date 设置此 Calendar 的时间。
//        rightNow.add(Calendar.MONTH, -1);// 日期减1个月
//        Date dt1 = rightNow.getTime();//返回一个表示此 Calendar 时间值的 Date 对象。
//        String reStr = sdf.format(dt1);//将给定的 Date 格式化为日期/时间字符串，并将结果添加到给定的 StringBuffer。
//        return reStr;
//    }
//
//    /**
//     * 日期加几月
//     */
//    public static String dateAddMonth(String str) throws Exception {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//        Date dt = sdf.parse(str);
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(dt);
//        rightNow.add(Calendar.MONTH, 1);// 日期加3个月
//        // rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
//        Date dt1 = rightNow.getTime();
//        String reStr = sdf.format(dt1);
//        return reStr;
//    }
//
//    /**
//     * 获取当前年月的第一个月的str
//     *
//     * @param str 201505
//     * @return 201501
//     * @throws Exception
//     */
//    public static String dateOneMonth(String str) {
//
//        str = str.substring(0, str.length() - 2);
//        str = str + "01";
//        return str;
//    }
//
//    /**
//     * 算出所选月份距离一月份有几个月。
//     *
//     * @param str 201509
//     * @return 9
//     */
//    public static int dateDistanceMonth(String str) {
//
//        int i = Integer.parseInt(str);
//        int j = Integer.parseInt(DateFormat.dateOneMonth(str));
//        System.out.println(i - j);
//        return i - j + 1;
//    }
//
//    /**
//     * 获取两个时间的时间差，精确到毫秒
//     *
//     * @param start
//     * @param end
//     * @return
//     */
//    public static String TimeDifference(long start, long end) {
//
//        long between = end - start;
//        long day = between / (24 * 60 * 60 * 1000);
//        long hour = (between / (60 * 60 * 1000) - day * 24);
//        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
//        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//        long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
//                - min * 60 * 1000 - s * 1000);
//        String timeDifference = day + "天" + hour + "小时" + min + "分" + s + "秒" + ms
//                + "毫秒";
//        return timeDifference;
//    }
//}
//
//    /**
//     * 获取24小时、一周、一个月的起始时间
//     *
//     * @param timeInterval : DAY_TIME_INTERVAL WEEK_TIME_INTERVAL MONTH_TIME_INTERVAL
//     * @return "yyyy-mm-dd hh:mm:ss"
//     */
//    public static String getStartTime(int timeInterval) {
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        if (DAY_TIME_INTERVAL == timeInterval) {// 获取24小时的起始时间
//            cal.set(Calendar.HOUR_OF_DAY, 0);
//            cal.set(Calendar.MINUTE, 0);
//            cal.set(Calendar.SECOND, 0);
//            String startTime = sdf.format(cal.getTime());
//            return startTime;
//        } else if (WEEK_TIME_INTERVAL == timeInterval) {
//            int weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
//            cal.add(Calendar.DATE, -weekday);
//            cal.set(Calendar.HOUR_OF_DAY, 0);
//            cal.set(Calendar.MINUTE, 0);
//            cal.set(Calendar.SECOND, 0);
//            String startTime = sdf.format(cal.getTime());
//            return startTime;
//        } else if (MONTH_TIME_INTERVAL == timeInterval) {
//            int dayofmonthMin = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
//            // c.add(Calendar.DATE, -dayofmonth);
//            cal.set(Calendar.DATE, dayofmonthMin);
//            cal.set(Calendar.HOUR_OF_DAY, 0);
//            cal.set(Calendar.MINUTE, 0);
//            cal.set(Calendar.SECOND, 0);
//            String startTime = sdf.format(cal.getTime());
//            return startTime;
//        }
//        return null;
//    }
//
//    /**
//     * 获取24小时、一周、一个月的结束时间
//     *
//     * @param timeInterval : DAY_TIME_INTERVAL WEEK_TIME_INTERVAL MONTH_TIME_INTERVAL
//     * @return "yyyy-mm-dd hh:mm:ss"
//     */
//    public static String getEndTime(int timeInterval) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        if (DAY_TIME_INTERVAL == timeInterval) {
//            cal.set(Calendar.HOUR_OF_DAY, 23);
//            cal.set(12, 59);
//            cal.set(13, 59);
//            long date = cal.getTimeInMillis();
//            String endTime = sdf.format(new Date(date));
//            return endTime;
//        } else if (WEEK_TIME_INTERVAL == timeInterval) {
//            int weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
//            cal.add(Calendar.DATE, -weekday);
//            cal.add(Calendar.DATE, 6);
//            cal.set(Calendar.HOUR_OF_DAY, 23);
//            cal.set(12, 59);
//            cal.set(13, 59);
//            long date = cal.getTimeInMillis();
//            String endTime = sdf.format(new Date(date));
//            return endTime;
//        } else if (MONTH_TIME_INTERVAL == timeInterval) {
//            int dayOfMonthMax = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//            cal.set(Calendar.DATE, dayOfMonthMax);
//            cal.set(Calendar.HOUR_OF_DAY, 23);
//            cal.set(Calendar.MINUTE, 59);
//            cal.set(Calendar.SECOND, 59);
//            String endTime = sdf.format(cal.getTime());
//            return endTime;
//        }
//        return null;
//    }
//
//    /**
//     * 判断dateStr是否在start和end中间，start和end都可以为null yyyyMMddHHmmss或者yyyyMMdd格式
//     *
//     * @param dateStr
//     * @param start
//     * @param end
//     * @return
//     * @author you.xu
//     * @date 2015年8月19日下午3:11:46
//     */
//    public static boolean checkDateVal(String dateStr, String start, String end) {
//        boolean isDateRight = false;
//        Date date = null;
//        Date startDate = null;
//        Date endDate = null;
//        SimpleDateFormat sdf = null;
//        // 判断日期格式
//        if (14 == dateStr.length()) {
//            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        } else if (8 == dateStr.length()) {
//            sdf = new SimpleDateFormat("yyyyMMdd");
//        } else
//            return false;
//
//        try {
//            // 更改判断日期格式
//            date = sdf.parse(dateStr);
//        } catch (ParseException e) {
//            log.error(e, e);
//        }
//
//        if ((start == null) && (end != null)) {
//            try {
//                endDate = sdf.parse(end);
//            } catch (ParseException ex1) {
//                log.error(ex1, ex1);
//            }
//            if ((date != null) && (endDate != null))// Check parameters for
//            {
//                if (date.compareTo(endDate) <= 0)
//                    isDateRight = true;
//            }
//        } else if ((start != null) && (end == null)) {
//            try {
//                startDate = sdf.parse(start);
//            } catch (ParseException ex1) {
//                log.error(ex1, ex1);
//            }
//            if ((date != null) && (startDate != null)) {
//                if (date.compareTo(startDate) >= 0)
//                    isDateRight = true;
//            }
//        } else if ((start != null) && (end != null)) {
//            try {
//                startDate = sdf.parse(start);
//                endDate = sdf.parse(end);
//            } catch (ParseException ex2) {
//                System.out.println(ex2.toString());
//            }
//            if ((startDate != null) && (date != null) && (endDate != null)) {
//                if ((date.compareTo(startDate) >= 0)
//                        && (date.compareTo(endDate) <= 0))
//                    isDateRight = true;
//            }
//        }
//        return isDateRight;
//    }
//
//    /**
//     * 判断dateStr是否在start和end中间，start和end都可以为null long形格式
//     *
//     * @param dateStr
//     * @param start
//     * @param end
//     * @return
//     * @author you.xu
//     * @date 2015年8月19日下午3:12:35
//     */
//    public static boolean checkDateV(String dateStr, String start, String end) {
//        boolean isDateRight = false;
//        long date = -1;
//        long fromDate = -1;
//        long toDate = -1;
//
//        date = java.lang.Long.parseLong(dateStr);
//
//        if ((start == null) && (end == null)) {
//            isDateRight = true;
//        } else if ((start == null) && (end != null)) {
//            try {
//                toDate = java.lang.Long.parseLong(end);
//            } catch (NumberFormatException nfe) {
//                log.error(nfe, nfe);
//            }
//            if (date <= toDate) {
//                isDateRight = true;
//            }
//        } else if ((start != null) && (end == null)) {
//            try {
//                fromDate = java.lang.Long.parseLong(start);
//            } catch (NumberFormatException nfe) {
//                log.error(nfe, nfe);
//            }
//
//            if (date >= fromDate) {
//                isDateRight = true;
//            }
//        } else if ((start != null) && (end != null)) {
//            try {
//                toDate = java.lang.Long.parseLong(end);
//                fromDate = java.lang.Long.parseLong(start);
//            } catch (NumberFormatException nfe) {
//                log.error(nfe, nfe);
//            }
//
//            if ((date <= toDate) && (date >= fromDate)) {
//                isDateRight = true;
//            }
//        }
//        return isDateRight;
//    }
