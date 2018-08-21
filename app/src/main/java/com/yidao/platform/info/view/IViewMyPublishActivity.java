package com.yidao.platform.info.view;

import com.yidao.platform.discovery.bean.FriendsShowBean;

import java.util.ArrayList;

public interface IViewMyPublishActivity {
    void loadMoreEnd(boolean b);

    void loadMoreComplete();

    void loadRecyclerData(ArrayList<FriendsShowBean> dataList);

    void loadMoreData(ArrayList<FriendsShowBean> dataList);

    void deleteSuccess(FriendsShowBean item);
}
