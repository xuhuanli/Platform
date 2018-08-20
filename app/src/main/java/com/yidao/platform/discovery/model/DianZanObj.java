package com.yidao.platform.discovery.model;

import android.support.annotation.NonNull;

public class DianZanObj {
    @NonNull
    private String findId;
    @NonNull
    private String userId;

    public DianZanObj() {
    }

    public DianZanObj(@NonNull String findId, @NonNull String userId) {
        this.findId = findId;
        this.userId = userId;
    }

    @NonNull
    public String getFindId() {
        return findId;
    }

    public void setFindId(@NonNull String findId) {
        this.findId = findId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }
}
