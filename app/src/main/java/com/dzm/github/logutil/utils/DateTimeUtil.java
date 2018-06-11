package com.dzm.github.logutil.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {
    private static final String TAG = "DateTimeUtil";

    public static final String DATE_PATTERN = "yyyyMMdd";
    public static final String DATE_PATTERN1 = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_PATTERN1 = "yyyyMMddHHmmss";
    public static final String DATE_TIME_PATTERN2 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_PATTERN3 = "yyyy/MM/dd HH:mm";
    public static final String DATE_TIME_PATTERN4 = "yyyy/MM/dd";
    public static final String DATE_TIME = "HH:mm";
    public static final String DATE_PATTERN2 = "MM月dd日";
    public static final long TEWELVE_HOURS_MILLISECONDS = 1000 * 60 * 60 * 12;
    public static final long DATE_SECOND = 1000 * 24 * 60 * 60;// 一天的毫秒数

    public static int getYearAtToday() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public static int getMonthAtToday() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDayAtToday() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static String formatTodayDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatTodayDate1() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN1);
        return sdf.format(new Date());
    }

    public static String formatDate1(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        return sdf.format(new Date(time));
    }


    public static String formatTodayDateTime() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN2);
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatDateTime(long time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN2);
            return sdf.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatAllDateTime() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTime(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(time, pos);
        return new SimpleDateFormat("HH:mm").format(strtodate);
    }

    /**
     * @param date
     * @return
     */
    public static String getDatabaseDateTime(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param date
     * @return
     */
    public static String getFileTimestamp(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN1);
        return sdf.format(date);
    }

    /**
     * @param dateTime
     * @return
     */
    public static Date parseAllDateTime(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        try {
            return sdf.parse(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * one day time
     *
     * @return
     */
    public static int distanceMidNightSeconds() {
        Calendar c = Calendar.getInstance();
        long curTimeMillis = c.getTimeInMillis();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        int distanceSeconds = (int) ((c.getTimeInMillis() - curTimeMillis) / 1000);
        return distanceSeconds;
    }

    /**
     * Away from the number of seconds after 12 hours
     *
     * @return
     */
    public static long getTweleveHourAfterSeconds() {
        return 12 * 60 * 60;
    }

    /**
     * @param pattern
     * @param datetime
     * @param millsSeconds
     * @return
     */
    public static boolean isCurrentTimeAfterMillsSeconds(String pattern,
                                                         String datetime, long millsSeconds) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date1;
        try {
            date1 = sdf.parse(datetime);
            return (new Date().getTime() - date1.getTime()) / millsSeconds > 0 ? true
                    : false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * if datetime is today, show date, otherwise show datetime
     *
     * @param datetime
     * @return
     */
    public static String showProperDatetime(String datetime) {
        if (datetime != null) {
            if (datetime.length() == 19) {
                try {
                    String dateStr = datetime.substring(0, 10);
                    if (dateStr.equals(DateTimeUtil.formatTodayDate1())) {
                        return datetime.substring(11, 16);
                    } else {
                        return datetime.substring(5, 10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (datetime.length() == 16) {
                try {
                    String dateStr = datetime.substring(0, 10);
                    if (dateStr.equals(DateTimeUtil.formatTodayDate1())) {
                        return datetime.substring(11, 16);
                    } else {
                        return datetime.substring(5, 10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return datetime;
    }

    public static boolean isBeforeNow(String intime, String dateFormat) {
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat(
                    dateFormat);
            Date date = myFormatter.parse(intime);
            Calendar cal = Calendar.getInstance();
            Date now1 = cal.getTime();
            if (date.before(now1)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param inFormat      输入的时间格式
     * @param inTime        输入的时间
     * @param outDateFormat 输出的日期格式
     * @param outTimeFormat 输出的时间格式
     * @return 如果时间为当天的时间，返回 时间，否则返回日期
     */
    public static String getStringTimeForInfoList(String inFormat,
                                                  String inTime, String outDateFormat, String outTimeFormat) {
        String infoTime = "";
        try {
            // 判断 输入时间是否为当天
            SimpleDateFormat myFormatter = new SimpleDateFormat(inFormat);
            Date infoDate = myFormatter.parse(inTime);
            SimpleDateFormat myDateFormatter = new SimpleDateFormat(
                    inFormat.split(" ")[0]);
            String infoDateStr = myDateFormatter.format(infoDate);
            String nowDateStr = myDateFormatter.format(new Date());
            if (infoDateStr.equals(nowDateStr)) {
                infoTime = new SimpleDateFormat(outTimeFormat).format(infoDate);
            } else {
                infoTime = new SimpleDateFormat(outDateFormat).format(infoDate);
            }
        } catch (ParseException e) {
        }
        return infoTime;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return yyyy年MM月dd日
     */
    public static String formateDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日");
        String strDate = format2.format(date);
        return strDate;
    }

    public static String formateDateE(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format2.format(date);
        return strDate;
    }

//    public static String getWeekOfDate(String str) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        Date date = null;
//        try {
//            date = format.parse(str);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return "";
//        }
//
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        int weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
//        if (weekday < 0) {
//            weekday = 0;
//        }
//        return weekDays[weekday];
//    }

//    static String[] weekDays =
//            {StringUtil.getString(R.string.date_week7),
//                    StringUtil.getString(R.string.date_week1),
//                    StringUtil.getString(R.string.date_week2),
//                    StringUtil.getString(R.string.date_week3),
//                    StringUtil.getString(R.string.date_week4),
//                    StringUtil.getString(R.string.date_week5),
//                    StringUtil.getString(R.string.date_week6)};

    /**
     * @param inFormat      输入的时间格式
     * @param inTime        输入的时间
     * @param outDateFormat 输出的日期格式
     * @return
     */
    public static String getStringTime(String inFormat, String inTime,
                                       String outDateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(inFormat);
        Date date = null;
        try {
            date = format.parse(inTime);
            SimpleDateFormat format2 = new SimpleDateFormat(outDateFormat);
            String strDate = format2.format(date);
            return strDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return inTime;
        }
    }

    public static long getTimeToMill(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN2);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //得到毫秒数
        long millTime = System.currentTimeMillis();
        try {
            millTime = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return millTime;
    }

    public static String getDataFromLong(long pushtime) {
        String date = "pushdate";
        long daytime = 1000 * 60 * 60 * 24;
        int curDay = new Long(System.currentTimeMillis() / daytime).intValue();
        int pushDay = new Long(pushtime / daytime).intValue();

        if (curDay == pushDay) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            date = sdf.format(new Date(pushtime));
            return date;
        } else if ((curDay - pushDay) == 1) {
            date = "昨天 ";
            return date;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
            date = sdf.format(new Date(pushtime));
            return date;
        }
    }

    public static String getDataFromLong2(long pushtime) {
        String date = "pushdate";
        long daytime = 1000 * 60 * 60 * 24;
        int curDay = new Long(System.currentTimeMillis() / daytime).intValue();
        int pushDay = new Long(pushtime / daytime).intValue();

        if (curDay == pushDay) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            date = sdf.format(new Date(pushtime));
            return date;
        } else if ((curDay - pushDay) == 1) {
            date = "昨天 ";
            return date;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.format(new Date(pushtime));
            return date;
        }
    }

    public static String getDataFromLong3(long pushtime) {
        long now = System.currentTimeMillis();
        Date pushDate = new Date(pushtime);
        Date nowDate = new Date(System.currentTimeMillis());
        Calendar pushCal = Calendar.getInstance();
        pushCal.setTime(pushDate);
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(nowDate);
        int diffDay = nowCal.get(Calendar.DAY_OF_YEAR) - pushCal.get(Calendar.DAY_OF_YEAR);

        if (now - pushtime <= 1000 * 60 * 60) {//一小时以内  刚刚
            return "刚刚";
        } else {
            if (pushCal.get(Calendar.YEAR) == nowCal.get(Calendar.YEAR)) {
                if (diffDay == 0) {//今天
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    return sdf.format(new Date(pushtime));
                } else if (diffDay == 1) {//昨天
                    return "昨天";
                } else {//其他显示几月几日
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                    return sdf.format(new Date(pushtime));
                }
            } else {
                nowCal.add(Calendar.DAY_OF_YEAR, -1);//增加跨年的昨天
                if (nowCal.get(Calendar.DAY_OF_YEAR) == pushCal.get(Calendar.DAY_OF_YEAR)) {
                    return "昨天";
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                    return sdf.format(new Date(pushtime));
                }
            }
        }
    }

    public static int calculateDays(long time) {
        long temp = System.currentTimeMillis() - time;
        return (int)(temp / 1000 / 60 / 60 / 24 ) + 1;
    }

    /**
     * 计算两次的时间差
     * @param oldTime
     * @param newTime
     * @return 相差的天数
     */
    public static int getDeff(String oldTime,String newTime) {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(DATE_PATTERN1);
        try {
            if(!TextUtils.isEmpty(oldTime)&&!TextUtils.isEmpty(newTime)){
                long mOldTime = mDateFormat.parse(oldTime).getTime();
                long mNewTime = mDateFormat.parse(newTime).getTime();
                return (int) ((mNewTime - mOldTime) / DATE_SECOND);
            }else if(TextUtils.isEmpty(oldTime)){
                return -1;
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算天数
     */
    public static String expireDays(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN4);
        try {
            Date d = sdf.parse(date);
            if (d != null) {
                long time = d.getTime();
                long currentTime = System.currentTimeMillis();
                long l = (time - currentTime) / 1000 / 60 / 60 / 24;
                Log.d(TAG, "expireDays: "+l);
                if(l<=60){
                    return String.valueOf(l);
                }else if(l>60){
                    return "days";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
