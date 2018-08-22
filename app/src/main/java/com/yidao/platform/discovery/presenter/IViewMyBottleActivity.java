package com.yidao.platform.discovery.presenter;

import com.yidao.platform.discovery.bean.MyBottleBean;

import java.util.List;

public interface IViewMyBottleActivity {

    void errorNet();

    void loadMoreEnd(boolean b);

    void loadMoreComplete();

    void loadRecyclerData(List<MyBottleBean.ListBean> dataList);

    void loadMoreData(List<MyBottleBean.ListBean> dataList);
}
