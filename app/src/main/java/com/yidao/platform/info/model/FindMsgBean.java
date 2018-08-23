package com.yidao.platform.info.model;

import java.util.List;

public class FindMsgBean {

    /**
     * status : true
     * errCode : 1000
     * info : success
     * result : {"list":[{"content":"pushtest","comment":"？","findId":"2468023404920832","userName":"However","userId":"1881601438449664","headImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/CmYfz0U8liafLF85XqXOvLiaHweLekX7XDC8f0R5OLY3EofzLunan86JeZFpAcEtyMKFLicq8bD3cKpvxZOg1ghCA/132","timeStamp":"5小时前","messageId":"2469872967155714"}],"page":{"pageIndex":"1","pageSize":"65535","total":"1","begIndex":"0"}}
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
         * list : [{"content":"pushtest","comment":"？","findId":"2468023404920832","userName":"However","userId":"1881601438449664","headImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/CmYfz0U8liafLF85XqXOvLiaHweLekX7XDC8f0R5OLY3EofzLunan86JeZFpAcEtyMKFLicq8bD3cKpvxZOg1ghCA/132","timeStamp":"5小时前","messageId":"2469872967155714"}]
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
             * content : pushtest
             * comment : ？
             * findId : 2468023404920832
             * userName : However
             * userId : 1881601438449664
             * headImg : http://thirdwx.qlogo.cn/mmopen/vi_32/CmYfz0U8liafLF85XqXOvLiaHweLekX7XDC8f0R5OLY3EofzLunan86JeZFpAcEtyMKFLicq8bD3cKpvxZOg1ghCA/132
             * timeStamp : 5小时前
             * messageId : 2469872967155714
             */

            private String content;
            private String comment;
            private String findId;
            private String userName;
            private String userId;
            private String headImg;
            private String timeStamp;
            private String messageId;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getFindId() {
                return findId;
            }

            public void setFindId(String findId) {
                this.findId = findId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getTimeStamp() {
                return timeStamp;
            }

            public void setTimeStamp(String timeStamp) {
                this.timeStamp = timeStamp;
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
