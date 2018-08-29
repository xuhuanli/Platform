package com.yidao.platform.read.view;

import com.yidao.platform.read.adapter.ReadNewsDetailBean;
import com.yidao.platform.read.bean.HotCommentsBean;
import com.yidao.platform.read.bean.ShareBean;

import java.util.ArrayList;
import java.util.List;

public interface IViewReadContentActivity {
    void deleteCommentSuccess();

    void deleteCommentFail();

    void pushCommentSuccess();

    void pushCommentFail();

    void loadMoreData(List<ReadNewsDetailBean> bean);

    void loadMoreEnd(boolean b);

    void loadMoreComplete();

    void showHotComment(boolean isCollectArt,String commentAmount, String likeAmount, ArrayList<ReadNewsDetailBean> dataList);

    void setShareContent(ShareBean.ResultBean result);
}
