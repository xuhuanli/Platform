package com.yidao.platform.discovery.presenter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.discovery.bean.MyBottleBean;

import java.util.List;

public class MyBottleAdapter extends BaseQuickAdapter<MyBottleBean.ListBean, BaseViewHolder> {
    public MyBottleAdapter(@Nullable List<MyBottleBean.ListBean> data) {
        super(R.layout.info_message_bottle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBottleBean.ListBean item) {
        helper
                .setImageResource(R.id.iv_touxiang, R.drawable.info_head_p)
                .setText(R.id.tv_name, "xhl")
                .setText(R.id.tv_comment_content, "这是瓶子的消息")
                .setText(R.id.tv_comment_time, "1小时前");
    }
}
