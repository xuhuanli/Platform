package com.yidao.platform.discovery.model;

public class ReplyBottleListObj {
    private String content;
    private String deviceId;
    private String sessionId;
    private String sessionType;
    private String userId;
    private String bottleId;

    public String getBottleId() {
        return bottleId;
    }

    public void setBottleId(String bottleId) {
        this.bottleId = bottleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
