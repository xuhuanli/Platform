package com.yidao.platform.discovery.bean;

import java.util.ArrayList;

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
    private String likeAmount;
    /**
     * 内容
     */
    private String content;
    /**
     * 图片地址
     */
    private ArrayList<String> imgUrls;

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

    public String getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(String likeAmount) {
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
}
