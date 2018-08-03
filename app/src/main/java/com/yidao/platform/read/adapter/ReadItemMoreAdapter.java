package com.yidao.platform.read.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

public class ReadItemMoreAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public ReadItemMoreAdapter(@Nullable List<String> data) {
        super(R.layout.read_mainpage_text_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.read_list_content, "状态栏布局和图标挺像Android,但是这白底黑字Android设计规范里可没有啊，于是我们开发的时候果断忽视这个状态栏了（当时大部分用户")
                .setText(R.id.tv_read_count, "12121阅读")
                .setText(R.id.tv_news_time, "6小时前");
        Glide.with(mContext).load(R.drawable.mypic).into((ImageView) helper.getView(R.id.read_list_image));
    }
}
