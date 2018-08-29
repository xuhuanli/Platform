package com.yidao.platform.discovery.view;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.yidao.platform.discovery.bean.FriendsShowBean;

import java.util.ArrayList;

public interface DiscoveryViewInterface {
    void showImage(Bitmap bitmap, ImageView view);

    void openCamera();

    void openAlbum();

    void showToast(String s);

    void loadMoreEnd(boolean b);

    void loadMoreComplete();

    void loadRecyclerData(ArrayList<FriendsShowBean> dataList);

    void loadMoreData(ArrayList<FriendsShowBean> dataList);

    void setEnableLoadMore(boolean b);

    void setRefreshing(boolean b);
}
