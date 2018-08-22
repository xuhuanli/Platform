package com.yidao.platform.login.model;

public class BindPhoneObj {
    private String phoneVerificationCode;
    private String userId;

    public BindPhoneObj() {
    }

    public BindPhoneObj(String phoneVerificationCode, String userId) {
        this.phoneVerificationCode = phoneVerificationCode;
        this.userId = userId;
    }

    public String getPhoneVerificationCode() {
        return phoneVerificationCode;
    }

    public void setPhoneVerificationCode(String phoneVerificationCode) {
        this.phoneVerificationCode = phoneVerificationCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
