package com.yidao.platform.info.model;

public class MineInfoBean {
    private String errCode;
    private String info;
    private ResultBean result;
    private String status;

    public static class ResultBean {
        private String mineCollect;
        private String mineFind;
        private String mineMessage;

        public String getMineCollect() {
            return mineCollect;
        }

        public void setMineCollect(String mineCollect) {
            this.mineCollect = mineCollect;
        }

        public String getMineFind() {
            return mineFind;
        }

        public void setMineFind(String mineFind) {
            this.mineFind = mineFind;
        }

        public String getMineMessage() {
            return mineMessage;
        }

        public void setMineMessage(String mineMessage) {
            this.mineMessage = mineMessage;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "mineCollect='" + mineCollect + '\'' +
                    ", mineFind='" + mineFind + '\'' +
                    ", mineMessage='" + mineMessage + '\'' +
                    '}';
        }
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MineInfoBean{" +
                "errCode='" + errCode + '\'' +
                ", info='" + info + '\'' +
                ", result=" + result +
                ", status='" + status + '\'' +
                '}';
    }
}
