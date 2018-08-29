package com.yidao.platform.info.view;

import com.yidao.platform.app.OssBean;
import com.yidao.platform.info.model.UserInfoBean;

public interface IViewPersonInfomationActivity {

    void saveOss(OssBean.ResultBean result);

    void successInfo(UserInfoBean.ResultBean result);

    void showError(String info);
}
