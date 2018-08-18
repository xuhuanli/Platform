package com.yidao.platform.discovery.model;

public class FindDiscoveryObj {

    /**
     * isContent : true
     * isImg : true
     * page : {"begIndex":0,"pageIndex":1,"pageSize":30,"total":0}
     * status : 1
     * memberId : 9291657025028096
     */

    private boolean isContent;
    private boolean isImg;
    private PageBean page;
    private int status;
    private long memberId;

    public boolean isIsContent() {
        return isContent;
    }

    public void setIsContent(boolean isContent) {
        this.isContent = isContent;
    }

    public boolean isIsImg() {
        return isImg;
    }

    public void setIsImg(boolean isImg) {
        this.isImg = isImg;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public static class PageBean {
        /**
         * begIndex : 0
         * pageIndex : 1
         * pageSize : 30
         * total : 0
         */

        private int begIndex;
        private int pageIndex;
        private int pageSize;
        private int total;

        public int getBegIndex() {
            return begIndex;
        }

        public void setBegIndex(int begIndex) {
            this.begIndex = begIndex;
        }

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
    }
}
