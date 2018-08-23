package com.yidao.platform.discovery.model;

import android.support.annotation.NonNull;

public class QryFindContentObj {
    /**
     * 朋友圈ID
     */
    @NonNull
    private String findId;
    /**
     * 当前登录用户Id
     */
    @NonNull
    private String memberId;

    public QryFindContentObj(@NonNull String findId, @NonNull String memberId) {
        this.findId = findId;
        this.memberId = memberId;
    }

    @NonNull
    public String getFindId() {
        return findId;
    }

    public void setFindId(@NonNull String findId) {
        this.findId = findId;
    }

    @NonNull
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(@NonNull String memberId) {
        this.memberId = memberId;
    }
}
