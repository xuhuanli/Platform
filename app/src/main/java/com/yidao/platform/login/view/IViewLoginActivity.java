package com.yidao.platform.login.view;

import com.yidao.platform.login.bean.WxCodeBean;

public interface IViewLoginActivity {
    void sendCodeSuccess();

    void bindSuccess(WxCodeBean.ResultBean result);

    void showInfo(String info);

    void hasBindToWx();

    void needRegister();
}
