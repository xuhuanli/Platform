package com.yidao.platform.events;

import android.graphics.Bitmap;

public class ThumbEvent {
    private Bitmap bitmap;
    private String title;
    private String subTitle;
    private String shareUrl;

    public ThumbEvent(Bitmap bitmap, String title, String subTitle) {
        this.bitmap = bitmap;
        this.title = title;
        this.subTitle = subTitle;
    }

    public ThumbEvent(Bitmap bitmap, String title, String subTitle,String shareUrl) {
        this.bitmap = bitmap;
        this.title = title;
        this.subTitle = subTitle;
        this.shareUrl = shareUrl;
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

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
