package com.yidao.platform.app.utils;

import java.util.Date;

public class TimeUtil {
    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;

    /**
     * @param timeValue
     * @return 距离现在
     */
    public static String fromToday(long timeValue) {
        long time = timeValue / 1000;
        long now = new Date().getTime() / 1000;
        long ago = now - time;
        if (ago < 0 || ago < ONE_MINUTE) {
            return "刚刚";
        } else if (ago <= ONE_HOUR) {
            return ago / ONE_MINUTE + "分钟前";
        } else if (ago <= ONE_DAY) {
            return ago / ONE_HOUR + "小时前";
        } else if (ago <= ONE_DAY * 2) {
            return "昨天";
        } else if (ago <= ONE_DAY * 3) {
            return "前天";
        } else if (ago <= ONE_MONTH) {
            long day = ago / ONE_DAY;
            if (day < 7)
                return day + "天前";
            else if (day < 14)
                return "1周前";
            else if (day < 21)
                return "2周前";
            else if (day < 28)
                return "3周前";
            else
                return "4周前";
        } else if (ago <= ONE_YEAR) {
            long month = ago / ONE_MONTH;
            return month + "个月前";
        } else {
            long year = ago / ONE_YEAR;
            return year + "年前";
        }
    }
}
