package com.yidao.platform.discovery.model;

import android.support.annotation.NonNull;

public class DeletePyqObj {
    @NonNull
    private String id;
    @NonNull
    private String memberId;

    public DeletePyqObj(@NonNull String id, @NonNull String memberId) {
        this.id = id;
        this.memberId = memberId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(@NonNull String memberId) {
        this.memberId = memberId;
    }
}
