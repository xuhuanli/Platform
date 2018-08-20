package com.yidao.platform.read.bean;

import android.os.Parcel;
import android.os.Parcelable;

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

    public static class ResultBean implements Parcelable {

        private String name;
        private long id;
        private long parentid;
        private String childList;
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

        public void setChildList(String childList) {
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
            return "BaseData{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", parentid=" + parentid +
                    ", childList=" + childList +
                    ", parente=" + parente +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeLong(this.id);
            dest.writeLong(this.parentid);
            dest.writeString(this.childList);
            dest.writeByte(this.parente ? (byte) 1 : (byte) 0);
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.name = in.readString();
            this.id = in.readLong();
            this.parentid = in.readLong();
            this.childList = in.readString();
            this.parente = in.readByte() != 0;
        }

        public static final Parcelable.Creator<ResultBean> CREATOR = new Parcelable.Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel source) {
                return new ResultBean(source);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };
    }

    @Override
    public String toString() {
        return "ChannelBean{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
