package com.yidao.platform.read.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ReadNewsDetailBean implements MultiItemEntity {
    public static final int ITEM_WEBVIEW = 1;
    public static final int ITEM_HOT_COMMENT = 2;
    public static final int ITEM_COMMENTS = 3;
    public static final int ITEM_LAST_COMMENT = 4;
    private int itemType;
    private String url;
    private String headImg;
    private String nickName;
    private String timeSamp;
    private String likeCount;
    private String content;
    private String id;
    private String userId;
    private boolean isLikedCommed;

    public boolean isLikedCommed() {
        return isLikedCommed;
    }

    public void setLikedCommed(boolean likedCommed) {
        isLikedCommed = likedCommed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTimeSamp() {
        return timeSamp;
    }

    public void setTimeSamp(String timeSamp) {
        this.timeSamp = timeSamp;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
