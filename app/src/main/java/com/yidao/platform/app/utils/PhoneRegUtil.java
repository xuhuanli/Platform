package com.yidao.platform.app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneRegUtil {
    /**
     * 验证手机号码
     *
     * @param phoneNumber 手机号码
     * @return boolean
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

}
