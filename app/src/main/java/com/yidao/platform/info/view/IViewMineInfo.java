package com.yidao.platform.info.view;

import com.yidao.platform.info.model.MineInfoBean;
import com.yidao.platform.info.model.UserInfoBean;

public interface IViewMineInfo {
    void success(MineInfoBean data);

    void successInfo(UserInfoBean.ResultBean result);

    void showError(String info);
}
