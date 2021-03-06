package com.yidao.platform.info.adapter;

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

public class RecentReadAdapter extends BaseQuickAdapter<ReadNewsBean, BaseViewHolder> {
    public RecentReadAdapter(@Nullable List<ReadNewsBean> data) {
        super(R.layout.recent_read_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadNewsBean item) {
        helper.setText(R.id.read_list_content, item.getTitle())
                .setText(R.id.tv_read_count, ConvertFormatUtil.convertCount(item.getReadAmount()))
                .setText(R.id.tv_news_time, item.getDeployTime());
        Glide.with(mContext).load(item.getHomeImg()).apply(new RequestOptions().placeholder(R.drawable.info_head_p).error(R.drawable.info_head_p)).into((ImageView) helper.getView(R.id.read_list_image));
    }
}
