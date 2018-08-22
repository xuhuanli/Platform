package com.yidao.platform.discovery.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PickBottleBean {

    /**
     * status : true
     * errCode : 1000
     * info : success
     * result : {"error":null,"result":1,"id":9518371072245760,"authorId":9266129287118848,"content":"徐书记是tiangou,哈哈哈哈哈哈哈哈！","auditStatus":1,"isDel":0,"status":1,"createTime":"2018-08-15","labelId":111,"labelName":"扯淡","sessionId":null,"userId":9266129287118848,"nickName":"徐书记","imgUrl":"https://ydplatform.oss-cn-hangzhou.aliyuncs.com/headImg/smer.png","address":"浙江杭州","age":28,"sex":1}
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

    public static class ResultBean implements Parcelable {
        /**
         * error : null
         * result : 1
         * id : 9518371072245760
         * authorId : 9266129287118848
         * content : 徐书记是tiangou,哈哈哈哈哈哈哈哈！
         * auditStatus : 1
         * isDel : 0
         * status : 1
         * createTime : 2018-08-15
         * labelId : 111
         * labelName : 扯淡
         * sessionId : null
         * userId : 9266129287118848
         * nickName : 徐书记
         * imgUrl : https://ydplatform.oss-cn-hangzhou.aliyuncs.com/headImg/smer.png
         * address : 浙江杭州
         * age : 28
         * sex : 1
         */

        private String error;
        private int result;
        private long id;
        private long authorId;
        private String content;
        private int auditStatus;
        private int isDel;
        private int status;
        private String createTime;
        private long labelId;
        private String labelName;
        private long sessionId;
        private long userId;
        private String nickName;
        private String imgUrl;
        private String address;
        private int age;
        private int sex;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getAuthorId() {
            return authorId;
        }

        public void setAuthorId(long authorId) {
            this.authorId = authorId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public long getLabelId() {
            return labelId;
        }

        public void setLabelId(long labelId) {
            this.labelId = labelId;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public long getSessionId() {
            return sessionId;
        }

        public void setSessionId(long sessionId) {
            this.sessionId = sessionId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
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

        @Override
        public String toString() {
            return "ResultBean{" +
                    "error='" + error + '\'' +
                    ", result=" + result +
                    ", id=" + id +
                    ", authorId=" + authorId +
                    ", content='" + content + '\'' +
                    ", auditStatus=" + auditStatus +
                    ", isDel=" + isDel +
                    ", status=" + status +
                    ", createTime='" + createTime + '\'' +
                    ", labelId=" + labelId +
                    ", labelName='" + labelName + '\'' +
                    ", sessionId=" + sessionId +
                    ", userId=" + userId +
                    ", nickName='" + nickName + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", address='" + address + '\'' +
                    ", age=" + age +
                    ", sex=" + sex +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.error);
            dest.writeInt(this.result);
            dest.writeLong(this.id);
            dest.writeLong(this.authorId);
            dest.writeString(this.content);
            dest.writeInt(this.auditStatus);
            dest.writeInt(this.isDel);
            dest.writeInt(this.status);
            dest.writeString(this.createTime);
            dest.writeLong(this.labelId);
            dest.writeString(this.labelName);
            dest.writeLong(this.sessionId);
            dest.writeLong(this.userId);
            dest.writeString(this.nickName);
            dest.writeString(this.imgUrl);
            dest.writeString(this.address);
            dest.writeInt(this.age);
            dest.writeInt(this.sex);
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.error = in.readString();
            this.result = in.readInt();
            this.id = in.readLong();
            this.authorId = in.readLong();
            this.content = in.readString();
            this.auditStatus = in.readInt();
            this.isDel = in.readInt();
            this.status = in.readInt();
            this.createTime = in.readString();
            this.labelId = in.readLong();
            this.labelName = in.readString();
            this.sessionId = in.readLong();
            this.userId = in.readLong();
            this.nickName = in.readString();
            this.imgUrl = in.readString();
            this.address = in.readString();
            this.age = in.readInt();
            this.sex = in.readInt();
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
}
