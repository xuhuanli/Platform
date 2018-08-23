package com.yidao.platform.info.model;

import java.util.List;

public class BottleMsgBean {

    /**
     * status : true
     * errCode : 1000
     * info : success
     * result : {"list":[{"bottleId":"9342485442199552","userId":" 69624474959872","content":"哈哈哈","createTime":"2018-08-23","sessionId":"1361680476471297","nickName":"TonyHo","address":"JPAkita-kenAkita-shi","timeStamp":"5小时前","headImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132","messageId":"2445413099044864"}],"page":{"pageIndex":"1","pageSize":"65535","total":"1","begIndex":"0"}}
     */

    private boolean status;
    private String errCode;
    private String info;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * list : [{"bottleId":"9342485442199552","userId":" 69624474959872","content":"哈哈哈","createTime":"2018-08-23","sessionId":"1361680476471297","nickName":"TonyHo","address":"JPAkita-kenAkita-shi","timeStamp":"5小时前","headImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132","messageId":"2445413099044864"}]
         * page : {"pageIndex":"1","pageSize":"65535","total":"1","begIndex":"0"}
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
             * pageSize : 65535
             * total : 1
             * begIndex : 0
             */

            private String pageIndex;
            private String pageSize;
            private String total;
            private String begIndex;

            public String getPageIndex() {
                return pageIndex;
            }

            public void setPageIndex(String pageIndex) {
                this.pageIndex = pageIndex;
            }

            public String getPageSize() {
                return pageSize;
            }

            public void setPageSize(String pageSize) {
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
        }

        public static class ListBean {
            /**
             * bottleId : 9342485442199552
             * userId :  69624474959872
             * content : 哈哈哈
             * createTime : 2018-08-23
             * sessionId : 1361680476471297
             * nickName : TonyHo
             * address : JPAkita-kenAkita-shi
             * timeStamp : 5小时前
             * headImg : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132
             * messageId : 2445413099044864
             */

            private String bottleId;
            private String userId;
            private String content;
            private String createTime;
            private String sessionId;
            private String nickName;
            private String address;
            private String timeStamp;
            private String headImg;
            private String messageId;

            public String getBottleId() {
                return bottleId;
            }

            public void setBottleId(String bottleId) {
                this.bottleId = bottleId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
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

            public String getSessionId() {
                return sessionId;
            }

            public void setSessionId(String sessionId) {
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

            public String getMessageId() {
                return messageId;
            }

            public void setMessageId(String messageId) {
                this.messageId = messageId;
            }
        }
    }
}
