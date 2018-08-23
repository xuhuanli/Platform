package com.yidao.platform.info.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoBean {

    /**
     * status : true
     * errCode : 200
     * info : 成功
     * result : {"id":"69624474959872","name":"","country":"JP","provinceName":"Akita-ken","cityName":"Akita-shi","areaName":"","address":"","phoneNum":"18958034893","nickname":"Tony","sex":"1","headImgUrl":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132","gmtCreate":"2018-08-16","gmtModified":"2018-08-22","lastLoginTime":"2018-08-22","status":1,"isDel":"0","firstDeviceType":"Android","firstLoginTime":"2018-08-16","commentStatus":"1","introduction":""}
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
         * id : 69624474959872
         * name :
         * country : JP
         * provinceName : Akita-ken
         * cityName : Akita-shi
         * areaName :
         * address :
         * phoneNum : 18958034893
         * nickname : Tony
         * sex : 1
         * headImgUrl : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocStBSia2hC2xaSOicQjIiaVHibU5DrVVbSu4kxey5B9b86X9son6kluyeibA6BsDibEdLm0jVAFxA9CCA/132
         * gmtCreate : 2018-08-16
         * gmtModified : 2018-08-22
         * lastLoginTime : 2018-08-22
         * status : 1
         * isDel : 0
         * firstDeviceType : Android
         * firstLoginTime : 2018-08-16
         * commentStatus : 1
         * introduction :
         */

        private String id;
        private String name;
        private String country;
        private String provinceName;
        private String cityName;
        private String areaName;
        private String address;
        private String phoneNum;
        private String nickname;
        private String sex;
        private String headImgUrl;
        private String gmtCreate;
        private String gmtModified;
        private String lastLoginTime;
        private int status;
        private String isDel;
        private String firstDeviceType;
        private String firstLoginTime;
        private String commentStatus;
        private String introduction;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(String gmtModified) {
            this.gmtModified = gmtModified;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getIsDel() {
            return isDel;
        }

        public void setIsDel(String isDel) {
            this.isDel = isDel;
        }

        public String getFirstDeviceType() {
            return firstDeviceType;
        }

        public void setFirstDeviceType(String firstDeviceType) {
            this.firstDeviceType = firstDeviceType;
        }

        public String getFirstLoginTime() {
            return firstLoginTime;
        }

        public void setFirstLoginTime(String firstLoginTime) {
            this.firstLoginTime = firstLoginTime;
        }

        public String getCommentStatus() {
            return commentStatus;
        }

        public void setCommentStatus(String commentStatus) {
            this.commentStatus = commentStatus;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.name);
            dest.writeString(this.country);
            dest.writeString(this.provinceName);
            dest.writeString(this.cityName);
            dest.writeString(this.areaName);
            dest.writeString(this.address);
            dest.writeString(this.phoneNum);
            dest.writeString(this.nickname);
            dest.writeString(this.sex);
            dest.writeString(this.headImgUrl);
            dest.writeString(this.gmtCreate);
            dest.writeString(this.gmtModified);
            dest.writeString(this.lastLoginTime);
            dest.writeInt(this.status);
            dest.writeString(this.isDel);
            dest.writeString(this.firstDeviceType);
            dest.writeString(this.firstLoginTime);
            dest.writeString(this.commentStatus);
            dest.writeString(this.introduction);
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.id = in.readString();
            this.name = in.readString();
            this.country = in.readString();
            this.provinceName = in.readString();
            this.cityName = in.readString();
            this.areaName = in.readString();
            this.address = in.readString();
            this.phoneNum = in.readString();
            this.nickname = in.readString();
            this.sex = in.readString();
            this.headImgUrl = in.readString();
            this.gmtCreate = in.readString();
            this.gmtModified = in.readString();
            this.lastLoginTime = in.readString();
            this.status = in.readInt();
            this.isDel = in.readString();
            this.firstDeviceType = in.readString();
            this.firstLoginTime = in.readString();
            this.commentStatus = in.readString();
            this.introduction = in.readString();
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
