package com.yidao.platform.read.view;

import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.bean.ReadNewsBean;
import com.yidao.platform.read.bean.CommonArticleBean;

import java.util.ArrayList;
import java.util.List;

public interface IViewReadFragment {
    void showBanner(List<String> imageUrls, List<String> bannerTitles);

    void loadMoreFail();

    void setEnableLoadMore(boolean b);

    void setRefreshing(boolean b);

    void showMainArticle(List<ReadNewsBean> dataList);

    void loadMoreEnd(boolean b);

    void loadMoreComplete();

    void loadMoreData(ArrayList<ReadNewsBean> dataList);

    void showError();

    void saveChannelData(ArrayList<ChannelBean.ResultBean> result);
}
