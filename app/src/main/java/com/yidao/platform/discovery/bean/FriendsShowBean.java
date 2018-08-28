package com.yidao.platform.discovery.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 朋友圈的展示Bean 类似于新闻ReadNewsBean
 */
public class FriendsShowBean {
    /**
     * 朋友圈ID
     */
    private String findId;
    /**
     * 发布人头像
     */
    private String headImg;
    /**
     * 发布人姓名
     */
    private String deployName;
    /**
     * 发布时间
     */
    private String deployTime;
    /**
     * 点赞数
     */
    private long likeAmount;
    /**
     * 是否点赞
     */
    private boolean isLike;
    /**
     * 内容
     */
    private String content;
    /**
     * 展示时间
     */
    private String timeStamp;
    /**
     * 图片地址
     */
    private ArrayList<String> imgUrls;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getFindId() {
        return findId;
    }

    public void setFindId(String findId) {
        this.findId = findId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getDeployName() {
        return deployName;
    }

    public void setDeployName(String deployName) {
        this.deployName = deployName;
    }

    public String getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(String deployTime) {
        this.deployTime = deployTime;
    }

    public long getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(long likeAmount) {
        this.likeAmount = likeAmount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(ArrayList<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    @Override
    public String toString() {
        return "FriendsShowBean{" +
                "findId='" + findId + '\'' +
                ", headImg='" + headImg + '\'' +
                ", deployName='" + deployName + '\'' +
                ", deployTime='" + deployTime + '\'' +
                ", likeAmount='" + likeAmount + '\'' +
                ", content='" + content + '\'' +
                ", imgUrls=" + imgUrls +
                '}';
    }
}
