package com.yidao.platform.info.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.info.model.BlackListBean;

import java.util.List;

public class BlackListAdapter extends BaseQuickAdapter<BlackListBean.ResultBean.ListBean, BaseViewHolder> {

    public BlackListAdapter(@Nullable List<BlackListBean.ResultBean.ListBean> data) {
        super(R.layout.discovery_my_bottle_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BlackListBean.ResultBean.ListBean item) {
        helper
                .setText(R.id.tv_name, item.getNickName())
                .setText(R.id.tv_comment_content, item.getAddress())
                .setVisible(R.id.tv_comment_time,false);
        Glide.with(mContext).load(item.getImgUrl()).apply(RequestOptions.placeholderOf(R.drawable.info_head_p)).into((ImageView) helper.getView(R.id.iv_touxiang));
    }
}
