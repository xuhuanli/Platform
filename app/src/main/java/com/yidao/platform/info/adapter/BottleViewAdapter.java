package com.yidao.platform.info.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

public class BottleViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BottleViewAdapter(@Nullable List<String> data) {
        super(R.layout.info_message_bottle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper
                .setImageResource(R.id.iv_touxiang, R.drawable.info_head_p)
                .setText(R.id.tv_name, "xhl")
                .setText(R.id.tv_comment_content, "这是瓶子的消息")
                .setText(R.id.tv_comment_time, "1小时前");
    }
}
