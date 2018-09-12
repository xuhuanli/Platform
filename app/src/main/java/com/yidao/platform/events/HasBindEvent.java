package com.yidao.platform.events;

public class HasBindEvent {
    private String phoneNum;
    private String vCode;

    public HasBindEvent(String phoneNum, String vCode) {
        this.phoneNum = phoneNum;
        this.vCode = vCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }
}
