package com.yidao.platform.read.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReadDetailAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public ReadDetailAdapter(@Nullable List<String> data) {
        super(R.layout.read_detail_comment_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(mContext).load(ContextCompat.getDrawable(mContext, R.drawable.info_head_p)).into((CircleImageView) helper.getView(R.id.iv_detail_icon));
        helper.setText(R.id.tv_detail_name, "xhl");
        helper.setText(R.id.tv_detail_comment, "好，支持，威武，有希望了");
        helper.setText(R.id.tv_detail_vote, "100");
        helper.setText(R.id.tv_detail_time, "10分钟前");
    }
}
