package com.yidao.platform.events;

import android.graphics.Bitmap;

public class ThumbEvent {
    private Bitmap bitmap;
    private String title;
    private String subTitle;

    public ThumbEvent(Bitmap bitmap, String title, String subTitle) {
        this.bitmap = bitmap;
        this.title = title;
        this.subTitle = subTitle;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
