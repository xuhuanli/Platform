package com.yidao.platform.discovery.presenter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.discovery.bean.MyBottleBean;

import java.util.List;

public class MyBottleAdapter extends BaseQuickAdapter<MyBottleBean.ListBean, BaseViewHolder> {
    public MyBottleAdapter(@Nullable List<MyBottleBean.ListBean> data) {
        super(R.layout.discovery_my_bottle_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBottleBean.ListBean item) {
        helper
                .setText(R.id.tv_name, item.getNickName())
                .setText(R.id.tv_comment_content, item.getContent())
                .setText(R.id.tv_comment_time, item.getTimeStamp());
        Glide.with(mContext).load(item.getHeadImg()).into((ImageView) helper.getView(R.id.iv_touxiang));
    }
}
