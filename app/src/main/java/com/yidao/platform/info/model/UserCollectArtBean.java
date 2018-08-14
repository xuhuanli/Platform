package com.yidao.platform.info.model;

import java.util.List;

public class UserCollectArtBean {
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
        private long id;
        private String title;
        private int status;
        private String homeImg;
        private int deployTime;
        private int type;
        private int readAmount;

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

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", status=" + status +
                    ", homeImg='" + homeImg + '\'' +
                    ", deployTime=" + deployTime +
                    ", type=" + type +
                    ", readAmount=" + readAmount +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserCollectArtBean{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
