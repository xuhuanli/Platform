package com.yidao.platform.info.model;

import java.util.List;

public class BlackListBean {

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

            private int pageIndex;
            private int pageSize;
            private String total;
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
        }

        public static class ListBean {
            /**
             * userId : 2894116863672320
             * nickName : 系统提示
             * imgUrl : https://ydplatform.oss-cn-hangzhou.aliyuncs.com/Find/IMG_1535173118482_73
             * address : Wuhan
             * age : 0
             * sex : 1
             */

            private String userId;
            private String nickName;
            private String imgUrl;
            private String address;
            private int age;
            private int sex;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }
        }
    }
}
