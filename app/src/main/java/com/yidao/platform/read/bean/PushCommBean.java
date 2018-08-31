package com.yidao.platform.read.bean;

public class PushCommBean {

    /**
     * status : true
     * result : {"id":"","topCommId":"","artId":"","parId":"","content":"测试发布评论返回用户信息","userId":"","parUserId":"","status":false,"createTime":"2018-08-30","updateTime":"2018-08-30","likeCount":"","commentUserName":"","commentUserHeadImgUrl":"http://thirdwx.qlogo.cn/mmopen/vi_32/ctI2ictkuicWn5k5ByCqyyBz8bS336S1yj9XP9L3HibtpGxzT2fHjqdHicxW7loDORUHdJ8d6t1mLLtrl41o4IjCUQ/132","time":"","nickname":"test 金立","isLikedCommed":false}
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
         * id :
         * topCommId :
         * artId :
         * parId :
         * content : 测试发布评论返回用户信息
         * userId :
         * parUserId :
         * status : false
         * createTime : 2018-08-30
         * updateTime : 2018-08-30
         * likeCount :
         * commentUserName :
         * commentUserHeadImgUrl : http://thirdwx.qlogo.cn/mmopen/vi_32/ctI2ictkuicWn5k5ByCqyyBz8bS336S1yj9XP9L3HibtpGxzT2fHjqdHicxW7loDORUHdJ8d6t1mLLtrl41o4IjCUQ/132
         * time :
         * nickname : test 金立
         * isLikedCommed : false
         */

        private String id;
        private String topCommId;
        private String artId;
        private String parId;
        private String content;
        private String userId;
        private String parUserId;
        private String status;
        private String createTime;
        private String updateTime;
        private String likeCount;
        private String commentUserName;
        private String commentUserHeadImgUrl;
        private String time;
        private String nickname;
        private boolean isLikedCommed;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public boolean isIsLikedCommed() {
            return isLikedCommed;
        }

        public void setIsLikedCommed(boolean isLikedCommed) {
            this.isLikedCommed = isLikedCommed;
        }
    }
}
