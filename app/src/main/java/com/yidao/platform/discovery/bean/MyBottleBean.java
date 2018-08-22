package com.yidao.platform.discovery.bean;

import java.util.List;

public class MyBottleBean {

    /**
     * list : [{"bottleId":9342485442199552,"userId":69624474959872,"content":"后台手动添加","createTime":"2018-08-22","sessionId":null,"nickName":"Tony","address":"JPAkita-kenAkita-shi","timeStamp":"0小时前","headImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132"},{"bottleId":9342485442199552,"userId":69624474959872,"content":"后台手动添加","createTime":"2018-08-22","sessionId":null,"nickName":"Tony","address":"JPAkita-kenAkita-shi","timeStamp":"0小时前","headImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132"},{"bottleId":9342485442199552,"userId":69624474959872,"content":"后台手动添加","createTime":"2018-08-22","sessionId":null,"nickName":"Tony","address":"JPAkita-kenAkita-shi","timeStamp":"0小时前","headImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132"},{"bottleId":9342485442199552,"userId":69624474959872,"content":"后台手动添加","createTime":"2018-08-22","sessionId":null,"nickName":"Tony","address":"JPAkita-kenAkita-shi","timeStamp":"0小时前","headImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132"},{"bottleId":9342485442199552,"userId":69624474959872,"content":"后台手动添加","createTime":"2018-08-22","sessionId":null,"nickName":"Tony","address":"JPAkita-kenAkita-shi","timeStamp":"0小时前","headImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132"}]
     * page : {"pageIndex":1,"pageSize":10,"total":5,"begIndex":0}
     */

    private PageBean page;
    private List<ListBean> list;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class PageBean {
        /**
         * pageIndex : 1
         * pageSize : 10
         * total : 5
         * begIndex : 0
         */

        private int pageIndex;
        private int pageSize;
        private int total;
        private int begIndex;

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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getBegIndex() {
            return begIndex;
        }

        public void setBegIndex(int begIndex) {
            this.begIndex = begIndex;
        }
    }

    public static class ListBean {
        /**
         * bottleId : 9342485442199552
         * userId : 69624474959872
         * content : 后台手动添加
         * createTime : 2018-08-22
         * sessionId : null
         * nickName : Tony
         * address : JPAkita-kenAkita-shi
         * timeStamp : 0小时前
         * headImg : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132
         */

        private long bottleId;
        private long userId;
        private String content;
        private String createTime;
        private long sessionId;
        private String nickName;
        private String address;
        private String timeStamp;
        private String headImg;

        public long getBottleId() {
            return bottleId;
        }

        public void setBottleId(long bottleId) {
            this.bottleId = bottleId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public long getSessionId() {
            return sessionId;
        }

        public void setSessionId(long sessionId) {
            this.sessionId = sessionId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }
    }
}
