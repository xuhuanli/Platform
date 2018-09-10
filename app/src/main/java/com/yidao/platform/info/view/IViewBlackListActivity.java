package com.yidao.platform.info.view;

import com.yidao.platform.info.model.BlackListBean;

import java.util.List;

public interface IViewBlackListActivity {

    void loadRecyclerData(List<BlackListBean.ResultBean.ListBean> dataList);

    void noBlackUser();

    void loadMoreEnd(boolean b);

    void loadMoreComplete();

    void loadMoreData(List<BlackListBean.ResultBean.ListBean> dataList);

    void cancelSuccess(BlackListBean.ResultBean.ListBean item);
}
