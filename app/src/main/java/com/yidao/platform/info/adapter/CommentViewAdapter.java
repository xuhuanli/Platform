package com.yidao.platform.info.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.info.model.FindMsgBean;

import java.util.List;

public class CommentViewAdapter extends BaseQuickAdapter<FindMsgBean.ResultBean.ListBean, BaseViewHolder> {

    public CommentViewAdapter(@Nullable List<FindMsgBean.ResultBean.ListBean> data) {
        super(R.layout.info_message_commemt_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FindMsgBean.ResultBean.ListBean item) {
        helper
                .setText(R.id.tv_name, item.getUserName())
                .setText(R.id.tv_comment_content, "评论了你: " + item.getComment())
                .setText(R.id.tv_comment_title, item.getContent())
                .setText(R.id.tv_comment_time, item.getTimeStamp());
        Glide.with(mContext).load(item.getHeadImg()).apply(RequestOptions.placeholderOf(R.drawable.info_head_p)).into((ImageView) helper.getView(R.id.iv_touxiang));
    }
}
