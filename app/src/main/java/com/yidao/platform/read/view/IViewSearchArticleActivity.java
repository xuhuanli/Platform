package com.yidao.platform.read.view;

import com.yidao.platform.read.bean.ReadNewsBean;

import java.util.ArrayList;

public interface IViewSearchArticleActivity {
    void showError();

    void loadRecyclerData(ArrayList<ReadNewsBean> dataList);

    void noData();

    void loadMoreEnd(boolean b);

    void loadMoreComplete();

    void loadMoreData(ArrayList<ReadNewsBean> dataList);
}
