package com.yidao.platform.login.model;

public class LoginObj {
    private String deviceId;
    private String deviceType;
    private String phone;
    private String phoneVerificationCode;

    public LoginObj(String deviceId, String deviceType, String phone, String phoneVerificationCode) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.phone = phone;
        this.phoneVerificationCode = phoneVerificationCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneVerificationCode() {
        return phoneVerificationCode;
    }

    public void setPhoneVerificationCode(String phoneVerificationCode) {
        this.phoneVerificationCode = phoneVerificationCode;
    }
}
