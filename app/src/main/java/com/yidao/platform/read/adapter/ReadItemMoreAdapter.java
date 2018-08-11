package com.yidao.platform.read.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.read.bean.ReadNewsBean;

import java.text.DecimalFormat;
import java.util.List;

public class ReadItemMoreAdapter extends BaseQuickAdapter<ReadNewsBean, BaseViewHolder> {

    public ReadItemMoreAdapter(@Nullable List<ReadNewsBean> data) {
        super(R.layout.read_mainpage_text_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadNewsBean item) {
        helper.setGone(R.id.iv_hot, false);
        String readAmount;

        DecimalFormat df = new DecimalFormat("0.00");
        long l = item.getReadAmount();
        readAmount = l > 1000 ? df.format(l / 1000) + "K阅" : l + "阅";
        int i = item.getDeployTime() / 24;
        String deployTime;
        deployTime = i < 0 ? i + "小时前" : i + "天前";
        helper.setText(R.id.read_list_content, item.getTitle())
                .setText(R.id.tv_read_count, readAmount)
                .setText(R.id.tv_news_time, deployTime);
        Glide.with(mContext).load(R.drawable.mypic).into((ImageView) helper.getView(R.id.read_list_image));
    }
}
