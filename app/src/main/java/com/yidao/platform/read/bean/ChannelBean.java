package com.yidao.platform.read.bean;

import java.util.List;

public class ChannelBean {

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

        private String name;
        private long id;
        private long parentid;
        private Object childList;
        private boolean parente;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getParentid() {
            return parentid;
        }

        public void setParentid(long parentid) {
            this.parentid = parentid;
        }

        public Object getChildList() {
            return childList;
        }

        public void setChildList(Object childList) {
            this.childList = childList;
        }

        public boolean isParente() {
            return parente;
        }

        public void setParente(boolean parente) {
            this.parente = parente;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", parentid=" + parentid +
                    ", childList=" + childList +
                    ", parente=" + parente +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ChannelBean{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
