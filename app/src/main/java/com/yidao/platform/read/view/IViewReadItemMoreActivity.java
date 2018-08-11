package com.yidao.platform.read.view;

import com.yidao.platform.read.bean.ReadNewsBean;

import java.util.ArrayList;
import java.util.List;

public interface IViewReadItemMoreActivity {

    void loadRecyclerData(List<ReadNewsBean> dataList);

    void loadMoreEnd(boolean b);

    void loadMoreComplete();

    void loadMoreData(List<ReadNewsBean> dataList);
}
