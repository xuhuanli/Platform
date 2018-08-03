package com.yidao.platform.discovery.presenter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

public class MyBottleAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public MyBottleAdapter(@Nullable List<String> data) {
        super(R.layout.info_message_bottle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper
                .setImageResource(R.id.iv_touxiang, R.drawable.mypic)
                .setText(R.id.tv_name, "xhl")
                .setText(R.id.tv_comment_content, "这是瓶子的消息")
                .setText(R.id.tv_comment_time, "1小时前");
    }
}
