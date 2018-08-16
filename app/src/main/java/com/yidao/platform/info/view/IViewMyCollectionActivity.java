package com.yidao.platform.info.view;

import com.yidao.platform.read.bean.ReadNewsBean;

import java.util.ArrayList;

public interface IViewMyCollectionActivity {

    void loadRecyclerData(ArrayList<ReadNewsBean> dataList);

    void showError();

    void loadMoreFail();

    void loadMoreEnd(boolean b);

    void loadMoreComplete();

    void loadMoreData(ArrayList<ReadNewsBean> dataList);
}
