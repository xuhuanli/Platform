package com.yidao.platform.discovery.view;

import com.yidao.platform.discovery.bean.PickBottleBean;

public interface IViewBottleActivity {
    void pickAnim();

    void pushAnim();

    void dismissSpaceShipWindow();

    void throwSuccess();

    void countLimit(String info);

    void getOneBottle(PickBottleBean.ResultBean result);

    void errorStatus(String info);

    void netError();

    void throwLimited(String info);
}
