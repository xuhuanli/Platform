package com.yidao.platform.read.bean;

import java.util.List;

public class BannerBean {

    /**
     * status : true
     * result : [{"id":6728066207973376,"imageUrl":"https://customize-test.oss-cn-hangzhou.aliyuncs.com/test/ERnnYD_1533807520501.pn","title":"这是图片标题123","url":"这是图片链接133","createTime":"2018-08-07","isDelete":0,"isLight":0,"sort":"1","uploadId":1},{"id":6728087607312384,"imageUrl":"https://customize-test.oss-cn-hangzhou.aliyuncs.com/test/ENn3na_1533176643758.jpg","title":"这是图片标题1234","url":"这是图片链接1334","createTime":"2018-08-07","isDelete":0,"isLight":0,"sort":"2","uploadId":1},{"id":6728131198713856,"imageUrl":"https://customize-test.oss-cn-hangzhou.aliyuncs.com/test/ED5hr8_1532586298557.jpg","title":"这是图片标题12345","url":"这是图片链接13345","createTime":"2018-08-07","isDelete":0,"isLight":0,"sort":"3","uploadId":1},{"id":6728149427159040,"imageUrl":"https://customize-test.oss-cn-hangzhou.aliyuncs.com/test/CJxGxK_1533176708528.jpg","title":"这是图片标题123456","url":"这是图片链接133456","createTime":"2018-08-07","isDelete":0,"isLight":0,"sort":"4","uploadId":1},{"id":6728167479443456,"imageUrl":"https://customize-test.oss-cn-hangzhou.aliyuncs.com/test/BpwG2N_1532595267864.jpg","title":"这是图片标题1234567","url":"这是图片链接1334567","createTime":"2018-08-07","isDelete":0,"isLight":0,"sort":"5","uploadId":1}]
     */

    private boolean status;
    private List<ResultBean> result;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 6728066207973376
         * imageUrl : https://customize-test.oss-cn-hangzhou.aliyuncs.com/test/ERnnYD_1533807520501.pn
         * title : 这是图片标题123
         * url : 这是图片链接133
         * createTime : 2018-08-07
         * isDelete : 0
         * isLight : 0
         * sort : 1
         * uploadId : 1
         */

        private long id;
        private String imageUrl;
        private String title;
        private String url;
        private String createTime;
        private int isDelete;
        private int isLight;
        private String sort;
        private int uploadId;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getIsLight() {
            return isLight;
        }

        public void setIsLight(int isLight) {
            this.isLight = isLight;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public int getUploadId() {
            return uploadId;
        }

        public void setUploadId(int uploadId) {
            this.uploadId = uploadId;
        }
    }
}
