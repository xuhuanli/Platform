package com.yidao.platform.read.view;

import com.yidao.platform.read.bean.ReadNewsBean;
import com.yidao.platform.read.bean.CommonArticleBean;

import java.util.List;

public interface IViewReadFragment {
    void showBanner(List<String> imageUrls, List<String> bannerTitles);

    void loadMoreFail();

    void loadMoreSuccess(CommonArticleBean ordinaryArticleBean);

    void setEnableLoadMore(boolean b);

    void setRefreshing(boolean b);

    void showMainArticle(List<ReadNewsBean> dataList);

    void loadMoreEnd(boolean b);

    void loadMoreComplete();
}
