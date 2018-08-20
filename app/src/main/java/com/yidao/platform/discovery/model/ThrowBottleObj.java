package com.yidao.platform.discovery.model;

import android.support.annotation.NonNull;

public class ThrowBottleObj {
    @NonNull
    private String authorId;
    @NonNull
    private String content;
    @NonNull
    private int labelId;
    @NonNull
    private int type;

    @NonNull
    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(@NonNull String authorId) {
        this.authorId = authorId;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    @NonNull
    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(@NonNull int labelId) {
        this.labelId = labelId;
    }

    @NonNull
    public int getType() {
        return type;
    }

    public void setType(@NonNull int type) {
        this.type = type;
    }
}
