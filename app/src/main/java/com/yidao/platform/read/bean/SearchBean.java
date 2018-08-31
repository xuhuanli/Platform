package com.yidao.platform.read.bean;

import java.util.List;

public class SearchBean {

    /**
     * status : true
     * result : {"pageIndex":1,"pageSize":6,"total":"","begIndex":0,"list":[{"id":4703482524794880,"title":"大学女生1000元生活费很滋润，为什么男生1500却紧巴巴呢？真实原因不敢相信","status":1,"homeImg":"http://ydplatform.oss-cn-hangzhou.aliyuncs.com/articleBanner/X8CsE8_1535533157718.jpg","deployTime":"1天前","type":0,"articleContent":"http://ydplatform.oss-cn-hangzhou.aliyuncs.com/1b867b94-ded8-45ac-995c-2640b80a70e220180829085923.html","readAmount":4703482524794880}],"status":true,"errCode":"1","info":""}
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
         * total :
         * begIndex : 0
         * list : [{"id":4703482524794880,"title":"大学女生1000元生活费很滋润，为什么男生1500却紧巴巴呢？真实原因不敢相信","status":1,"homeImg":"http://ydplatform.oss-cn-hangzhou.aliyuncs.com/articleBanner/X8CsE8_1535533157718.jpg","deployTime":"1天前","type":0,"articleContent":"http://ydplatform.oss-cn-hangzhou.aliyuncs.com/1b867b94-ded8-45ac-995c-2640b80a70e220180829085923.html","readAmount":4703482524794880}]
         * status : true
         * errCode : 1
         * info :
         */

        private int pageIndex;
        private int pageSize;
        private String total;
        private int begIndex;
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

        public int getBegIndex() {
            return begIndex;
        }

        public void setBegIndex(int begIndex) {
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
             * id : 4703482524794880
             * title : 大学女生1000元生活费很滋润，为什么男生1500却紧巴巴呢？真实原因不敢相信
             * status : 1
             * homeImg : http://ydplatform.oss-cn-hangzhou.aliyuncs.com/articleBanner/X8CsE8_1535533157718.jpg
             * deployTime : 1天前
             * type : 0
             * articleContent : http://ydplatform.oss-cn-hangzhou.aliyuncs.com/1b867b94-ded8-45ac-995c-2640b80a70e220180829085923.html
             * readAmount : 4703482524794880
             */

            private long id;
            private String title;
            private int status;
            private String homeImg;
            private String deployTime;
            private long type;
            private String articleContent;
            private long readAmount;

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

            public long getType() {
                return type;
            }

            public void setType(long type) {
                this.type = type;
            }

            public String getArticleContent() {
                return articleContent;
            }

            public void setArticleContent(String articleContent) {
                this.articleContent = articleContent;
            }

            public long getReadAmount() {
                return readAmount;
            }

            public void setReadAmount(long readAmount) {
                this.readAmount = readAmount;
            }
        }
    }
}
