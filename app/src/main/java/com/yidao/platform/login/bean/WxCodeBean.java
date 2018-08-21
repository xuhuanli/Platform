package com.yidao.platform.login.bean;

public class WxCodeBean {
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
        private long userId;
        private String nickname;
        private int sex;
        private String country;
        private String province;
        private String city;
        private String headImgUrl;
        private String privilegeList;
        private String token;
        private String refreshToken;
        private boolean firstLogin;
        private boolean bindPhone;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public Object getPrivilegeList() {
            return privilegeList;
        }

        public void setPrivilegeList(String privilegeList) {
            this.privilegeList = privilegeList;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public boolean isFirstLogin() {
            return firstLogin;
        }

        public void setFirstLogin(boolean firstLogin) {
            this.firstLogin = firstLogin;
        }

        public boolean isBindPhone() {
            return bindPhone;
        }

        public void setBindPhone(boolean bindPhone) {
            this.bindPhone = bindPhone;
        }
    }
}
