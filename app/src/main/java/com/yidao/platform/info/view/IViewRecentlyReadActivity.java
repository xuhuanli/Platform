package com.yidao.platform.info.view;

import com.yidao.platform.read.bean.ReadNewsBean;

import java.util.ArrayList;

public interface IViewRecentlyReadActivity {
    void showError();

    void loadRecyclerData(ArrayList<ReadNewsBean> dataList);
}
