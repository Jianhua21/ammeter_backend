package com.kashuo.kcp.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final String formatPattern = "yyyy-MM-dd";

    private static final String formatPattern_year_month = "yyyy-MM";

    private static final String formatPatternDateTime = "yyyy-MM-dd HH:mm:ss";

    static final String formatPattern_Short = "yyyyMMdd";

    /**
     * 获取当前日期
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(new Date());
    }
    public static String getLastDayDate(){
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
        return format.format(calendar.getTime());
    }

    /**
     * 获取不同格式的当前日期
     */
    public static String getCurrentDate(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat format = new SimpleDateFormat(formatPatternDateTime);
        return format.format(new Date());
    }

    /**
     * 获取制定毫秒数之前的日期
     */
    public static String getDesignatedDate(long timeDiff) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        long nowTime = System.currentTimeMillis();
        long designTime = nowTime - timeDiff;
        return format.format(designTime);
    }

    /**
     * 获取前几天的日期
     */
    public static String getPrefixDate(String count) {
        Calendar cal = Calendar.getInstance();
        int day = 0 - Integer.parseInt(count);
        cal.add(Calendar.DATE, day); // int amount 代表天数
        Date datNew = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(datNew);
    }

    /**
     * 日期转换成字符串
     */
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(date);
    }

    /**
     * 日期转换成字符串
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) return "";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 字符串转换日期
     */
    public static Date stringToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        if (str != null && !str.equals("")) {
            try {
                return format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static Date stringYearMonthToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern_year_month);
        if (str != null && !str.equals("")) {
            try {
                return format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 字符串转换日期
     */
    public static Date stringToDate(String dateStr, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        if (dateStr != null && !dateStr.equals("")) {
            try {
                return format.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int getWeeks(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static  int getHour(){
        SimpleDateFormat format = new SimpleDateFormat("HH");
        return Integer.parseInt(format.format(new Date()));
    }
    public static int getYear(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return Integer.parseInt(format.format(new Date()));
    }
    public static int getMonth(){
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return Integer.parseInt(format.format(new Date()));
    }

    public static String getYearMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(new Date());
    }

    // java中怎样计算两个时间如：“21:57”和“08:20”相差的分钟数、小时数 java计算两个时间差小时 分钟 秒 .
    public void timeSubtract() {
        SimpleDateFormat dfs = new SimpleDateFormat(formatPatternDateTime);
        Date begin = null;
        Date end = null;
        try {
            begin = dfs.parse("2004-01-02 11:30:24");
            end = dfs.parse("2004-03-26 13:31:40");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60;
        System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
    }

    /**
     * 对`时间字串`进行日期或时间偏移量加工处理
     *
     * @param timeString 时间字串 yyyy-MM-dd HH:mm:ss 格式
     * @param field      Calendar类字段，如：YEAR, MONTH, DATE, DAY_OF_MONTH, HOUR, MINUTE, SECOND...
     * @param amount     计算要添加到字段的日期或时间偏移量
     * @return 最终时间字串
     */
    public static String machiningTimeString(String timeString, int field, int amount) throws Exception {
        SimpleDateFormat sd = new SimpleDateFormat(formatPatternDateTime);
        Calendar now = Calendar.getInstance();
        now.setTime(sd.parse(timeString));
        now.add(field, amount);
        return sd.format(now.getTime());
    }

    /**
     * 关于SimpleDateFormat安全的时间格式化线程安全问题
     */
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static Date parse(String dateStr) throws ParseException {
        return threadLocal.get().parse(dateStr);
    }

    public static String format(Date date) {
        return threadLocal.get().format(date);
    }

    public static void main(String[] argv) throws Exception {
//        System.out.println(getWeeks(stringToDate("2016-04-11")));
//        System.out.println(getWeeks(stringToDate("2016-04-12")));
//        System.out.println(getWeeks(stringToDate("2016-04-13")));
//        System.out.println(getWeeks(stringToDate("2016-04-14")));
//        System.out.println(getWeeks(stringToDate("2016-04-15")));
//        System.out.println(getWeeks(stringToDate("2016-04-16")));
        System.out.println(getLastDayDate());
    }

    //根据当前时间获取当天的起始日期
    public static Long getDateLongTimeStart(Date date) {
        String dateStr = dateToString(date) + " 00:00:00";
        return stringToDate(dateStr, formatPatternDateTime).getTime();
    }

    //根据当前时间获取当天的终值日期
    public static Long getDateLongTimeEnd(Date date) {
        String dateStr = dateToString(date) + " 23:59:59";
        return stringToDate(dateStr, formatPatternDateTime).getTime();
    }


}
