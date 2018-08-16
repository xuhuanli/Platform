package com.yidao.platform.read.bean;

import java.util.List;

public class CategoryArticleExtBean {

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

        private int pageIndex;
        private int pageSize;
        private int total;
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

        public Object getInfo() {
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

            private long id;
            private String title;
            private int status;
            private String homeImg;
            private int deployTime;
            private int type;
            private int readAmount;
            private String articleContent;

            public String getArticleContent() {
                return articleContent;
            }

            public void setArticleContent(String articleContent) {
                this.articleContent = articleContent;
            }

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

            public int getDeployTime() {
                return deployTime;
            }

            public void setDeployTime(int deployTime) {
                this.deployTime = deployTime;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getReadAmount() {
                return readAmount;
            }

            public void setReadAmount(int readAmount) {
                this.readAmount = readAmount;
            }
        }
    }
}
