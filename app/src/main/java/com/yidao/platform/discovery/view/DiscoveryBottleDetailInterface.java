package com.yidao.platform.discovery.view;

import com.yidao.platform.discovery.bean.BottleDtlBean;

public interface DiscoveryBottleDetailInterface {

    void showErrorInfo(String info);

    void commentSuccess();

    void showBottleDtl(BottleDtlBean.ResultBean result);
}
