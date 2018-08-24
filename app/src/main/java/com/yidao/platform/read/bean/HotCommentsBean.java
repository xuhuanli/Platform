package com.yidao.platform.read.bean;

import java.util.List;

public class HotCommentsBean {

    /**
     * status : true
     * result : {"articleContent":"https://ydplatform.oss-cn-hangzhou.aliyuncs.com/article/haha%20%281%29.html","shareAmount":"1220","readAmount":"10064","likeAmount":"122","commentAmount":"22","cmsArticleCommentDtos":[{"id":"21","topCommId":"","artId":"","parId":"","content":"评论6号","userId":"","parUserId":"","status":0,"createTime":"2018-08-08","updateTime":"","likeCount":"","commentUserName":"","commentUserHeadImgUrl":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132","time":"","nickname":"Tony"}]}
     */

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
        /**
         * articleContent : https://ydplatform.oss-cn-hangzhou.aliyuncs.com/article/haha%20%281%29.html
         * shareAmount : 1220
         * readAmount : 10064
         * likeAmount : 122
         * commentAmount : 22
         * cmsArticleCommentDtos : [{"id":"21","topCommId":"","artId":"","parId":"","content":"评论6号","userId":"","parUserId":"","status":0,"createTime":"2018-08-08","updateTime":"","likeCount":"","commentUserName":"","commentUserHeadImgUrl":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132","time":"","nickname":"Tony"}]
         */

        private String articleContent;
        private String shareAmount;
        private String readAmount;
        private String likeAmount;
        private String commentAmount;
        private List<CmsArticleCommentDtosBean> cmsArticleCommentDtos;

        public String getArticleContent() {
            return articleContent;
        }

        public void setArticleContent(String articleContent) {
            this.articleContent = articleContent;
        }

        public String getShareAmount() {
            return shareAmount;
        }

        public void setShareAmount(String shareAmount) {
            this.shareAmount = shareAmount;
        }

        public String getReadAmount() {
            return readAmount;
        }

        public void setReadAmount(String readAmount) {
            this.readAmount = readAmount;
        }

        public String getLikeAmount() {
            return likeAmount;
        }

        public void setLikeAmount(String likeAmount) {
            this.likeAmount = likeAmount;
        }

        public String getCommentAmount() {
            return commentAmount;
        }

        public void setCommentAmount(String commentAmount) {
            this.commentAmount = commentAmount;
        }

        public List<CmsArticleCommentDtosBean> getCmsArticleCommentDtos() {
            return cmsArticleCommentDtos;
        }

        public void setCmsArticleCommentDtos(List<CmsArticleCommentDtosBean> cmsArticleCommentDtos) {
            this.cmsArticleCommentDtos = cmsArticleCommentDtos;
        }

        public static class CmsArticleCommentDtosBean {
            /**
             * id : 21
             * topCommId :
             * artId :
             * parId :
             * content : 评论6号
             * userId :
             * parUserId :
             * status : 0
             * createTime : 2018-08-08
             * updateTime :
             * likeCount :
             * commentUserName :
             * commentUserHeadImgUrl : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132
             * time :
             * nickname : Tony
             */

            private String id;
            private String topCommId;
            private String artId;
            private String parId;
            private String content;
            private String userId;
            private String parUserId;
            private int status;
            private String createTime;
            private String updateTime;
            private String likeCount;
            private String commentUserName;
            private String commentUserHeadImgUrl;
            private int time;
            private String nickname;

            public String getId() {
                return id;
            }

            public void setId(String id) {
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
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

            public String getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(String likeCount) {
                this.likeCount = likeCount;
            }

            public String getCommentUserName() {
                return commentUserName;
            }

            public void setCommentUserName(String commentUserName) {
                this.commentUserName = commentUserName;
            }

            public String getCommentUserHeadImgUrl() {
                return commentUserHeadImgUrl;
            }

            public void setCommentUserHeadImgUrl(String commentUserHeadImgUrl) {
                this.commentUserHeadImgUrl = commentUserHeadImgUrl;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}
