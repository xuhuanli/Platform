package com.yidao.platform.discovery.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.discovery.bean.FriendsNewsBean;

import java.util.List;

@Deprecated
public class AnimationDiscoveryAdapter extends BaseQuickAdapter<FriendsNewsBean,BaseViewHolder>{

    private Context mContext;

    public AnimationDiscoveryAdapter(Context context,@Nullable List<FriendsNewsBean> data){
        super(R.layout.discovery_friends_item,data);
        mContext = context;
    }

    public AnimationDiscoveryAdapter(int layoutResId, @Nullable List<FriendsNewsBean> data) {
        super(R.layout.discovery_friends_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendsNewsBean item) {
        Glide.with(mContext).load(R.drawable.mypic).into((ImageView) helper.getView(R.id.iv_discovery_icon));
//        helper.setText(R.id.tv_discovery_name,"xhl");
//        helper.setText(R.id.tv_discovery_time,"11:39");
//        helper.setText(R.id.tv_discovery_vote,"10001");
//        helper.setText(R.id.tv_discovery_content,"杀菌付款后多少空间划分");
//        Glide.with(mContext).load(R.drawable.a).into((ImageView) helper.getView(R.id.iv_photo_1));
//        Glide.with(mContext).load(R.drawable.b).into((ImageView) helper.getView(R.id.iv_photo_2));
//        Glide.with(mContext).load(R.drawable.c).into((ImageView) helper.getView(R.id.iv_photo_3));
    }
}
