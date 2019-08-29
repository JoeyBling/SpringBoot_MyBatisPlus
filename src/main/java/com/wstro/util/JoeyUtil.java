package com.wstro.util;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public class JoeyUtil {

    /**
     * 如果为空或undefined返回空
     *
     * @param flag String
     * @return String
     */
    public static String NullOrUndefined(String flag) {
        if (null == flag || flag.equals("undefined")) {
            return null;
        }
        return flag;
    }

    /**
     * Java 替换字符串中的回车换行符
     *
     * @param myString 原字符串
     * @return 替换后字符串
     */
    public static String parseTxtByRN(String myString) {
        String newString = null;
        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
        Matcher m = CRLF.matcher(myString);
        if (m.find()) {
            newString = m.replaceAll("   ");
        }
        return newString == null ? myString : newString;
    }

    /**
     * 转换格式数组
     */
    static SimpleDateFormat[] simpleDateFormat = new SimpleDateFormat[10];

    /**
     * 静态初始化
     */
    static {
        simpleDateFormat[0] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat[1] = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat[2] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        simpleDateFormat[3] = new SimpleDateFormat("yyyy/MM/dd");
        simpleDateFormat[4] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        simpleDateFormat[5] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        simpleDateFormat[6] = new SimpleDateFormat("yyyy年/MM月/dd日");
        simpleDateFormat[7] = new SimpleDateFormat("yyyy年MM月dd日");
        simpleDateFormat[8] = new SimpleDateFormat("yyyy年-MM月-dd日");
        simpleDateFormat[9] = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒");
    }

    /**
     * 正则表达式验证
     *
     * @param regex 正则表达式
     * @param input 字符串
     * @return 返回是否符合正则表达式
     */
    public static boolean regex(String regex, CharSequence input) {
        if (null == input) {
            return false;
        }
        boolean flag = true;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        if (!m.find()) {
            flag = false;
        }
        return flag;
    }

    /**
     * 将时间转换为时间戳
     *
     * @param s 时间
     * @return 时间戳
     * @throws ParseException
     */
    public static long dateToStamp(String s) throws ParseException {
        Date date = new Date();
        for (SimpleDateFormat flag : simpleDateFormat) {
            try {
                date = flag.parse(s);
                break;
            } catch (Exception e) {
            }
        }
        long ts = date.getTime() / 1000;
        return ts;
    }

    /**
     * 将时间戳转换为时间
     *
     * @param s 时间戳
     * @return 时间
     */
    public static String stampToDateString(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间
     *
     * @param s          时间戳
     * @param dataFormat 格式化
     * @return 时间
     */
    public static String stampToDateString(String s, String dataFormat) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormat);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为日期
     *
     * @param s 时间戳
     * @return 日期
     */
    public static Date stampToDate(String s) {
        long lt = new Long(s);
        Date date = new Date(lt);
        return date;
    }

    /**
     * 获得当天0点时间
     *
     * @return Date
     */
    public static Date getTimesmorning() {
        // 获得今天时间
        Calendar c = Calendar.getInstance();

        // 将时，分，秒，毫秒设置为0
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        // 此处返回的为今天的零点的毫秒数
        return new Date(c.getTimeInMillis());
    }

    /**
     * 获得当天0点时间加上自定义天数(Integer day)
     *
     * @param day 自定义天数
     * @return Date
     */
    public static Date getTimesByDay(Integer day) {
        if (null == day) {
            return null;
        }
        // 获得今天时间
        Calendar c = Calendar.getInstance();

        // 将时，分，秒，毫秒设置为0
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, day);
        // 此处返回的为今天的零点的毫秒数
        return new Date(c.getTimeInMillis());
    }

    /**
     * 获得当天时间加上自定义天数(Integer day)
     *
     * @param day 自定义天数
     * @return Date
     */
    public static Date getTimeByDay(Integer day) {
        if (null == day) {
            return null;
        }
        // 获得今天时间
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, day);
        // 此处返回的为今天的零点的毫秒数
        return new Date(c.getTimeInMillis());
    }

    /**
     * 将时间转换为Long类型的时间戳
     *
     * @param date
     * @return
     * @throws NumberFormatException
     * @throws ParseException
     */
    public static Long stampDate(Date date, String format) throws NumberFormatException, ParseException {
        return JoeyUtil.dateToStamp(DateUtils.format(date, format));
    }

    /**
     * 将Long 类型的时间戳转换为Date类型时间
     *
     * @param time
     * @return
     */
    public static Date fomartDate(Long time) {
        return JoeyUtil.stampToDate(Long.toString(time));
    }

    /**
     * ToByteArray
     *
     * @param obj Object
     * @return byte[]
     */
    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * ToObject
     *
     * @param bytes byte[]
     * @return Object
     */
    public static Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

}
