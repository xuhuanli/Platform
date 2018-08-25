package com.yidao.platform.read.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ReadNewsBean implements MultiItemEntity {
    public static final int ITEM_ONE = 1;
    public static final int ITEM_TWO = 2;
    private int itemType;

    /**
     * 文章所属类目id
     */
    private long categoryId;
    /**
     * 文章ID
     */
    private long id;
    /**
     * 文章title
     */
    private String title;
    /**
     * 文章图片
     */
    private String homeImg;
    /**
     * 文章发布时间
     */
    private String deployTime;
    /**
     *  文章类型
     */
    private int type;
    /**
     * 文章阅读数
     */
    private long readAmount;
    /**
     * 文章URL
     */
    private String articleContent;

    public ReadNewsBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return this.itemType;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHomeImg() {
        return homeImg;
    }

    public void setHomeImg(String homeImg) {
        this.homeImg = homeImg;
    }

    public String getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(String deployTime) {
        this.deployTime = deployTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getReadAmount() {
        return readAmount;
    }

    public void setReadAmount(long readAmount) {
        this.readAmount = readAmount;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    @Override
    public String toString() {
        return "ReadNewsBean{" +
                "itemType=" + itemType +
                ", categoryId=" + categoryId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", homeImg='" + homeImg + '\'' +
                ", deployTime=" + deployTime +
                ", type=" + type +
                ", readAmount=" + readAmount +
                ", articleContent='" + articleContent + '\'' +
                '}';
    }
}
