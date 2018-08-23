package com.yidao.platform.discovery.bean;

import java.util.List;

public class FindContentBean {

    /**
     * status : true
     * result : {"id":"2468023404920832","userId":"1881601438449664","userName":"","headImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/CmYfz0U8liafLF85XqXOvLiaHweLekX7XDC8f0R5OLY3EofzLunan86JeZFpAcEtyMKFLicq8bD3cKpvxZOg1ghCA/132","content":"pushtest","status":"1","createTime":"2018-08-23","isLike":false,"likeAmount":1,"commAmount":3,"imgs":["","",""]}
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
         * id : 2468023404920832
         * userId : 1881601438449664
         * userName :
         * headImg : http://thirdwx.qlogo.cn/mmopen/vi_32/CmYfz0U8liafLF85XqXOvLiaHweLekX7XDC8f0R5OLY3EofzLunan86JeZFpAcEtyMKFLicq8bD3cKpvxZOg1ghCA/132
         * content : pushtest
         * status : 1
         * createTime : 2018-08-23
         * isLike : false
         * likeAmount : 1
         * commAmount : 3
         * imgs : ["","",""]
         */

        private String id;
        private String userId;
        private String userName;
        private String headImg;
        private String content;
        private String status;
        private String createTime;
        private boolean isLike;
        private int likeAmount;
        private int commAmount;
        private List<String> imgs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public boolean isIsLike() {
            return isLike;
        }

        public void setIsLike(boolean isLike) {
            this.isLike = isLike;
        }

        public int getLikeAmount() {
            return likeAmount;
        }

        public void setLikeAmount(int likeAmount) {
            this.likeAmount = likeAmount;
        }

        public int getCommAmount() {
            return commAmount;
        }

        public void setCommAmount(int commAmount) {
            this.commAmount = commAmount;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }
}
