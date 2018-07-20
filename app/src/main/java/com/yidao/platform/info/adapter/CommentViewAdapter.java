package com.yidao.platform.info.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

public class CommentViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CommentViewAdapter(@Nullable List<String> data) {
        super(R.layout.info_message_commemt_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_test,"这是评论内容");
    }
}
