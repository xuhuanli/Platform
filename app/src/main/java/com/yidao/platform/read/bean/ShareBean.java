package com.yidao.platform.read.bean;

public class ShareBean {

    /**
     * status : true
     * errCode : 1000
     * info : 操作成功
     * result : {"id":"4703482524794880","title":"大学女生1000元生活费很滋润，为什么男生1500却紧巴巴呢？真实原因不敢相信","subtitle":"叨叨奥","homeImg":"http://ydplatform.oss-cn-hangzhou.aliyuncs.com/articleBanner/X8CsE8_1535533157718.jpg"}
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
         * id : 4703482524794880
         * title : 大学女生1000元生活费很滋润，为什么男生1500却紧巴巴呢？真实原因不敢相信
         * subtitle : 叨叨奥
         * homeImg : http://ydplatform.oss-cn-hangzhou.aliyuncs.com/articleBanner/X8CsE8_1535533157718.jpg
         */

        private String id;
        private String title;
        private String subtitle;
        private String homeImg;
        private String linkUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getHomeImg() {
            return homeImg;
        }

        public void setHomeImg(String homeImg) {
            this.homeImg = homeImg;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }
    }
}
