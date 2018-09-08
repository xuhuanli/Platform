package com.yidao.platform.discovery.model;

import android.support.annotation.NonNull;

public class PyqFindIdObj {
    private String findId;

    public PyqFindIdObj(@NonNull String findId) {
        this.findId = findId;
    }

    public String getFindId() {
        return findId;
    }

    public void setFindId(String findId) {
        this.findId = findId;
    }
}
