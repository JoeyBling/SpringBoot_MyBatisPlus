package com.wstro.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化所有格式
     */
    private final static SimpleDateFormat[] simpleDateFormat = new SimpleDateFormat[6];

    static { // 静态初始化
        simpleDateFormat[0] = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat[1] = new SimpleDateFormat("yyyy年MM月dd日");
        simpleDateFormat[2] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat[3] = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        simpleDateFormat[4] = new SimpleDateFormat("yyyy/MM/dd");
        simpleDateFormat[5] = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    }

    /**
     * 转换日期为String使用默认的格式(yyyy-MM-dd)
     *
     * @param date Date
     * @return String
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 转换日期为String
     *
     * @param date    Date
     * @param pattern 转换的格式
     * @return String
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 将字符串转为日期
     *
     * @param date 字符串
     * @return Date
     * @throws Exception
     */
    public static Date parse(String date) throws Exception {
        if (date != null) {
            for (SimpleDateFormat df : simpleDateFormat) {
                try {
                    return df.parse(date);
                } catch (ParseException e) {
                    continue;
                }
            }

        }
        throw new Exception("转换失败");
    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳
     */
    public static Long getCurrentUnixTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 判断日期是否是周末
     *
     * @param d 日期
     * @return 是否是周末
     */
    public static boolean isWeekend(Date d) {
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.setTime(d);

        int i = myCalendar.get(Calendar.DAY_OF_WEEK);
        // 星期日i==1，星期六i==7
        if (i == 1 || i == 7) {
            return true;
        }
        return false;
    }

    /**
     * 判断日期是否是周末
     *
     * @param d yyyy-MM-dd
     * @return 是否是周末
     * @throws Exception
     */
    public static boolean isWeekend(String d) throws Exception {
        return DateUtils.isWeekend(DateUtils.parse(d));
    }

    /**
     * 判断日期是否是周末
     *
     * @param timestamp 时间戳
     * @return 是否是周末
     * @throws Exception
     */
    public static boolean isWeekendByTimestamp(String timestamp) throws Exception {
        return DateUtils.isWeekend(JoeyUtil.stampToDate(timestamp));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

}
