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
        helper
                .setImageResource(R.id.iv_touxiang,R.drawable.mypic)
                .setText(R.id.tv_name,"xhl")
        .setText(R.id.tv_comment_content,"评论了你：今年资产管理新规出台以后，银行…")
        .setText(R.id.tv_comment_title,"这会带来一系列连锁反应会带来一系列连锁反应会带来一系列连锁反应会带来一系列连锁反应")
        .setText(R.id.tv_comment_time,"1小时前");
    }
}
