package com.yidao.platform.read.bean;

import java.util.List;

public class ArticleCommentsBean {
    private boolean status;
    private ResultBean result;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private CmsArticleContentExtBean cmsArticleContentExt;
        private int shareAmount;
        private long readAmount;
        private int likeAmount;
        private int commentAmount;
        private List<CmsArticleCommentDtosBean> cmsArticleCommentDtos;

        public CmsArticleContentExtBean getCmsArticleContentExt() {
            return cmsArticleContentExt;
        }

        public void setCmsArticleContentExt(CmsArticleContentExtBean cmsArticleContentExt) {
            this.cmsArticleContentExt = cmsArticleContentExt;
        }

        public int getShareAmount() {
            return shareAmount;
        }

        public void setShareAmount(int shareAmount) {
            this.shareAmount = shareAmount;
        }

        public long getReadAmount() {
            return readAmount;
        }

        public void setReadAmount(long readAmount) {
            this.readAmount = readAmount;
        }

        public int getLikeAmount() {
            return likeAmount;
        }

        public void setLikeAmount(int likeAmount) {
            this.likeAmount = likeAmount;
        }

        public int getCommentAmount() {
            return commentAmount;
        }

        public void setCommentAmount(int commentAmount) {
            this.commentAmount = commentAmount;
        }

        public List<CmsArticleCommentDtosBean> getCmsArticleCommentDtos() {
            return cmsArticleCommentDtos;
        }

        public void setCmsArticleCommentDtos(List<CmsArticleCommentDtosBean> cmsArticleCommentDtos) {
            this.cmsArticleCommentDtos = cmsArticleCommentDtos;
        }

        public static class CmsArticleContentExtBean {
            private long id;
            private String title;
            private String createTime;
            private int status;
            private int isDel;
            private int auditStatus;
            private String subtitle;
            private String homeImg;
            private String deployTime;
            private Object updateTime;
            private Object userId;
            private int type;
            private Object updateId;
            private String delTime;
            private int delUserId;
            private int deployId;
            private int auditId;
            private String articleContent;

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

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
                this.userId = userId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public Object getUpdateId() {
                return updateId;
            }

            public void setUpdateId(Object updateId) {
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

            public String getArticleContent() {
                return articleContent;
            }

            public void setArticleContent(String articleContent) {
                this.articleContent = articleContent;
            }
        }

        public static class CmsArticleCommentDtosBean {
            private int id;
            private String topCommId;
            private String artId;
            private String parId;
            private String content;
            private long userId;
            private String parUserId;
            private int status;
            private String createTime;
            private String updateTime;
            private int likeCount;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTopCommId() {
                return topCommId;
            }

            public void setTopCommId(String topCommId) {
                this.topCommId = topCommId;
            }

            public String getArtId() {
                return artId;
            }

            public void setArtId(String artId) {
                this.artId = artId;
            }

            public String getParId() {
                return parId;
            }

            public void setParId(String parId) {
                this.parId = parId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getParUserId() {
                return parUserId;
            }

            public void setParUserId(String parUserId) {
                this.parUserId = parUserId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }
        }
    }
}
