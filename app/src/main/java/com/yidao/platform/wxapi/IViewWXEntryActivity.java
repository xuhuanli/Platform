package com.yidao.platform.wxapi;

import com.yidao.platform.login.bean.WxCodeBean;

interface IViewWXEntryActivity {
    void loginFail();

    void loginFail(String info);

    void loginSuccess(WxCodeBean.ResultBean result);
}
