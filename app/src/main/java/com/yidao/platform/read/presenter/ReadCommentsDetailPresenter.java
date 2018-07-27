package com.yidao.platform.read.presenter;

import com.yidao.platform.discovery.bean.CommentItem;
import com.yidao.platform.discovery.presenter.BasePresenter;
import com.yidao.platform.read.view.ReadCommentsDetailInterface;

@Deprecated
public class ReadCommentsDetailPresenter extends BasePresenter {

    private ReadCommentsDetailInterface view;

    public ReadCommentsDetailPresenter(ReadCommentsDetailInterface view) {
        this.view = view;
    }

    @Override
    public void deleteComment(CommentItem commentItem) {
        view.update2DeleteComment(commentItem);
    }
}
