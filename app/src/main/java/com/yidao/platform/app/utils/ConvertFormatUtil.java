package com.yidao.platform.app.utils;

public class ConvertFormatUtil {

    public static String convertCount(long count) {
        /*DecimalFormat df = new DecimalFormat("0.0");
        return count > 1000f ? df.format(count / 1000D) + "K阅" : count + "阅";*/
        return count + "阅";
    }

    public static String convertTime(int hour) {
        int i = hour / 24;
        return i < 0 ? hour + "小时前" : i + "天前";
    }
}
