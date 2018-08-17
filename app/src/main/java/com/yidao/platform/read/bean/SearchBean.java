package com.yidao.platform.read.bean;

import java.util.List;

public class SearchBean {
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

        private int id;
        private String title;
        private String createTime;
        private int status;
        private int isDel;
        private int auditStatus;
        private String subtitle;
        private String homeImg;
        private String deployTime;
        private String updateTime;
        private int userId;
        private int type;
        private int updateId;
        private String delTime;
        private int delUserId;
        private int deployId;
        private int auditId;
        private String author;
        private String fromUrl;
        private String articleContent;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
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

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUpdateId() {
            return updateId;
        }

        public void setUpdateId(int updateId) {
            this.updateId = updateId;
        }

        public String getDelTime() {
            return delTime;
        }

        public void setDelTime(String delTime) {
            this.delTime = delTime;
        }

        public int getDelUserId() {
            return delUserId;
        }

        public void setDelUserId(int delUserId) {
            this.delUserId = delUserId;
        }

        public int getDeployId() {
            return deployId;
        }

        public void setDeployId(int deployId) {
            this.deployId = deployId;
        }

        public int getAuditId() {
            return auditId;
        }

        public void setAuditId(int auditId) {
            this.auditId = auditId;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getFromUrl() {
            return fromUrl;
        }

        public void setFromUrl(String fromUrl) {
            this.fromUrl = fromUrl;
        }

        public String getArticleContent() {
            return articleContent;
        }

        public void setArticleContent(String articleContent) {
            this.articleContent = articleContent;
        }
    }
}
