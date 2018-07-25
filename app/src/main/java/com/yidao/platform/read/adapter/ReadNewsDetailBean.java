package com.yidao.platform.read.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ReadNewsDetailBean implements MultiItemEntity {
    public static final int ITEM_WEBVIEW = 1;
    public static final int ITEM_HOT_COMMENT = 2;
    public static final int ITEM_COMMENTS = 3;
    public static final int ITEM_BOTTOM = 4;
    private int itemType;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ReadNewsDetailBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return this.itemType;
    }
}
