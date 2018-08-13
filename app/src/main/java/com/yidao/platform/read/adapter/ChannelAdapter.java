package com.yidao.platform.read.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.read.bean.ChannelBean;

import java.util.List;

public class ChannelAdapter extends BaseQuickAdapter<ChannelBean.ResultBean, BaseViewHolder> {
    public ChannelAdapter(@Nullable List<ChannelBean.ResultBean> data) {
        super(R.layout.read_channel_card_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelBean.ResultBean item) {
        helper.setText(R.id.tv_channel,item.getName());
    }
}
