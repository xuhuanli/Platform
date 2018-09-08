package com.yidao.platform.read.view;

import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.bean.ReadNewsBean;

import java.util.ArrayList;
import java.util.List;

public interface IViewReadFragment {
    void showBanner(List<String> imageUrls, List<String> bannerTitles, List<String> artUrls, List<Long> artIds);

    void loadMoreFail();

    void setEnableLoadMore(boolean b);

    void setRefreshing(boolean b);

    void showMainArticle(List<ReadNewsBean> dataList);

    void loadMoreEnd(boolean b);

    void loadMoreComplete();

    void loadMoreData(ArrayList<ReadNewsBean> dataList);

    void showError();

    void saveChannelData(ArrayList<ChannelBean.ResultBean> result);

    void netError();
}
