package com.yidao.platform.card.bean;

import com.yidao.platform.discovery.bean.FriendsListBean;

import java.util.List;

public class UploadCardBean {

//    errCode	1000申请成功,其他失败	string
//    info		string
//    result		number
//    status		boolean

    private String errCode;
    private String info;
    private int  result;
    private boolean status;

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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
