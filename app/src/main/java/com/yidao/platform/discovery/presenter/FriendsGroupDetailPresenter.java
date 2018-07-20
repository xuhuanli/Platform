package com.yidao.platform.discovery.presenter;

import com.yidao.platform.discovery.FriendsGroupDetailInterface;
import com.yidao.platform.discovery.bean.CommentItem;

public class FriendsGroupDetailPresenter extends BasePresenter {

    private FriendsGroupDetailInterface view;

    public FriendsGroupDetailPresenter(FriendsGroupDetailInterface view) {
        this.view = view;
    }

    @Override
    public void deleteComment(CommentItem commentItem) {
        view.update2DeleteComment(commentItem);
    }
}
