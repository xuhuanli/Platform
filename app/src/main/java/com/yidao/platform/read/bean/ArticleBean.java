package com.yidao.platform.read.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleBean {

    private boolean status;
    private List<ResultBean> result;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {

        private long categoryId;
        private List<ArticleExtListBean> articleExtList;

        public long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(long categoryId) {
            this.categoryId = categoryId;
        }

        public List<ArticleExtListBean> getArticleExtList() {
            return articleExtList;
        }

        public void setArticleExtList(List<ArticleExtListBean> articleExtList) {
            this.articleExtList = articleExtList;
        }

        public static class ArticleExtListBean {

            private long id;
            private String title;
            private int status;
            private String homeImg;
            @SerializedName(value = "time")
            private String deployTime;
            private int type;
            private long readAmount;
            private String articleContent;

            public String getArticleContent() {
                return articleContent;
            }

            public void setArticleContent(String articleContent) {
                this.articleContent = articleContent;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            @Override
            public String toString() {
                return "ArticleExtListBean{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", status=" + status +
                        ", homeImg='" + homeImg + '\'' +
                        ", deployTime=" + deployTime +
                        ", type=" + type +
                        ", readAmount=" + readAmount +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    ", categoryId=" + categoryId +
                    ", articleExtList=" + articleExtList +
                    '}';
        }
    }
}
