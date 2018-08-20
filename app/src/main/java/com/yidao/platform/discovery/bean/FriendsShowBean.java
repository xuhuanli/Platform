package com.yidao.platform.discovery.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 朋友圈的展示Bean 类似于新闻ReadNewsBean
 */
public class FriendsShowBean implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.findId);
        dest.writeString(this.headImg);
        dest.writeString(this.deployName);
        dest.writeString(this.deployTime);
        dest.writeString(this.likeAmount);
        dest.writeString(this.content);
        dest.writeStringList(this.imgUrls);
    }

    public FriendsShowBean() {
    }

    protected FriendsShowBean(Parcel in) {
        this.findId = in.readString();
        this.headImg = in.readString();
        this.deployName = in.readString();
        this.deployTime = in.readString();
        this.likeAmount = in.readString();
        this.content = in.readString();
        this.imgUrls = in.createStringArrayList();
    }

    public static final Parcelable.Creator<FriendsShowBean> CREATOR = new Parcelable.Creator<FriendsShowBean>() {
        @Override
        public FriendsShowBean createFromParcel(Parcel source) {
            return new FriendsShowBean(source);
        }

        @Override
        public FriendsShowBean[] newArray(int size) {
            return new FriendsShowBean[size];
        }
    };

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
