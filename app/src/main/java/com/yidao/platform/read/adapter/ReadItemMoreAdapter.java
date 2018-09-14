package com.yidao.platform.read.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.app.utils.ConvertFormatUtil;
import com.yidao.platform.read.bean.ReadNewsBean;

import java.util.List;

public class ReadItemMoreAdapter extends BaseQuickAdapter<ReadNewsBean, BaseViewHolder> {

    public ReadItemMoreAdapter(@Nullable List<ReadNewsBean> data) {
        super(R.layout.read_mainpage_text_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadNewsBean item) {
        helper.setGone(R.id.iv_hot, false);

        String readAmount = ConvertFormatUtil.convertCount(item.getReadAmount());
        String deployTime = item.getDeployTime();
        helper.setText(R.id.read_list_content, item.getTitle())
                .setText(R.id.tv_read_count, readAmount)
                .setText(R.id.tv_news_time, deployTime);
        Glide.with(mContext).load(item.getHomeImg()).apply(RequestOptions.placeholderOf(R.drawable.info_head_p)).into((ImageView) helper.getView(R.id.read_list_image));
    }
}
