package com.yidao.platform.discovery.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.discovery.DiscoveryFragment;
import com.yidao.platform.discovery.bean.FriendsShowBean;

import java.util.List;

import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

public class MomentAdapter extends BaseQuickAdapter<FriendsShowBean, BaseViewHolder> {
    private BGANinePhotoLayout.Delegate delegate;
    public MomentAdapter(@Nullable List<FriendsShowBean> data,BGANinePhotoLayout.Delegate delegate) {
        super(R.layout.discovery_friends_item, data);
        this.delegate = delegate;
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendsShowBean item) {
        helper.setText(R.id.tv_discovery_name,item.getDeployName())
                .setText(R.id.tv_discovery_time,item.getDeployTime())
                .setText(R.id.tv_discovery_vote,item.getLikeAmount())
                .setText(R.id.tv_discovery_content,item.getContent());
        Glide.with(mContext).load(item.getHeadImg()).apply(new RequestOptions().placeholder(R.drawable.info_head_p).error(R.drawable.info_head_p)).into((ImageView) helper.getView(R.id.iv_discovery_icon));
        BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.npl_item_moment_photos);
        ninePhotoLayout.setDelegate(delegate);
        ninePhotoLayout.setData(item.getImgUrls());
    }
}
