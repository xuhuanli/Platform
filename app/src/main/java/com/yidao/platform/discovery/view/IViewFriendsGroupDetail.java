package com.yidao.platform.discovery.view;

import com.yidao.platform.discovery.bean.CommentsItem;
import com.yidao.platform.discovery.bean.FriendsShowBean;

import java.util.ArrayList;

public interface IViewFriendsGroupDetail {
    void update2DeleteComment(CommentsItem commentId);

    void showComments(ArrayList<CommentsItem> dataList);

    void addCommentSuccess();

    void showDetail(FriendsShowBean showBean);
}
