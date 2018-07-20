package com.yidao.platform.discovery.presenter;

import com.yidao.platform.discovery.bean.CommentItem;
import com.yidao.platform.discovery.view.DiscoveryBottleDetailInterface;

public class DiscoveryBottleDetailPresenter extends BasePresenter{
    private DiscoveryBottleDetailInterface view;

    public DiscoveryBottleDetailPresenter(DiscoveryBottleDetailInterface view) {
        this.view = view;
    }

    @Override
    public void deleteComment(CommentItem commentItem) {
        view.update2DeleteComment(commentItem);
    }
}
