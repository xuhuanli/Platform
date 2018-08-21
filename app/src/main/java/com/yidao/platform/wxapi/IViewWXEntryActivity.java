package com.yidao.platform.wxapi;

import com.yidao.platform.login.bean.WxCodeBean;

public interface IViewWXEntryActivity {
    void loginFail();

    void loginSuccess(WxCodeBean.ResultBean result);
}
