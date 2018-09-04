package com.yidao.platform.events;

import com.yidao.platform.login.bean.WxCodeBean;

public class WxSignInEvent {
    private WxCodeBean.ResultBean result;

    public WxSignInEvent() {
    }

    public WxSignInEvent(WxCodeBean.ResultBean result) {
        this.result = result;
    }

    public WxCodeBean.ResultBean getResult() {
        return result;
    }

    public void setResult(WxCodeBean.ResultBean result) {
        this.result = result;
    }
}
