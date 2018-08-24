package com.yidao.platform.read.bean;

import java.util.List;

public class LastCommentsBean {

    /**
     * status : true
     * result : {"pageIndex":1,"pageSize":6,"total":"22","begIndex":"0","list":[{"id":"11","topCommId":"12","artId":"6709842980503552","parId":"12","content":"评论7号","userId":"2894116863672320","parUserId":"1","status":0,"createTime":"2018-08-08","updateTime":"2018-08-08","likeCount":"7","commentUserName":"","commentUserHeadImgUrl":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epszWc5wfcsQ211Kb8la4iaZiav3IYuayxuwN10fBhRA1KzDL4wpojZXcEYicPTdQ9rbhn0A01mZk9RA/132","time":"","nickname":"Emphse"}],"status":true,"errCode":"1","info":""}
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
         * pageIndex : 1
         * pageSize : 6
         * total : 22
         * begIndex : 0
         * list : [{"id":"11","topCommId":"12","artId":"6709842980503552","parId":"12","content":"评论7号","userId":"2894116863672320","parUserId":"1","status":0,"createTime":"2018-08-08","updateTime":"2018-08-08","likeCount":"7","commentUserName":"","commentUserHeadImgUrl":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epszWc5wfcsQ211Kb8la4iaZiav3IYuayxuwN10fBhRA1KzDL4wpojZXcEYicPTdQ9rbhn0A01mZk9RA/132","time":"","nickname":"Emphse"}]
         * status : true
         * errCode : 1
         * info :
         */

        private int pageIndex;
        private int pageSize;
        private String total;
        private String begIndex;
        private boolean status;
        private String errCode;
        private String info;
        private List<ListBean> list;

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getBegIndex() {
            return begIndex;
        }

        public void setBegIndex(String begIndex) {
            this.begIndex = begIndex;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getErrCode() {
            return errCode;
        }

        public void setErrCode(String errCode) {
            this.errCode = errCode;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 11
             * topCommId : 12
             * artId : 6709842980503552
             * parId : 12
             * content : 评论7号
             * userId : 2894116863672320
             * parUserId : 1
             * status : 0
             * createTime : 2018-08-08
             * updateTime : 2018-08-08
             * likeCount : 7
             * commentUserName :
             * commentUserHeadImgUrl : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epszWc5wfcsQ211Kb8la4iaZiav3IYuayxuwN10fBhRA1KzDL4wpojZXcEYicPTdQ9rbhn0A01mZk9RA/132
             * time :
             * nickname : Emphse
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
