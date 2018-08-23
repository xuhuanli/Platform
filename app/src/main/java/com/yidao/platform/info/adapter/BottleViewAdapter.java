package com.yidao.platform.info.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.info.model.BottleMsgBean;

import java.util.List;

public class BottleViewAdapter extends BaseQuickAdapter<BottleMsgBean.ResultBean.ListBean, BaseViewHolder> {

    public BottleViewAdapter(@Nullable List<BottleMsgBean.ResultBean.ListBean> data) {
        super(R.layout.info_message_bottle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BottleMsgBean.ResultBean.ListBean item) {
        helper
                .setText(R.id.tv_name, item.getNickName())
                .setText(R.id.tv_comment_content, item.getContent())
                .setText(R.id.tv_comment_time, item.getTimeStamp());
        Glide.with(mContext).load(item.getHeadImg()).apply(RequestOptions.placeholderOf(R.drawable.info_head_p)).into((ImageView) helper.getView(R.id.iv_touxiang));
    }
}
