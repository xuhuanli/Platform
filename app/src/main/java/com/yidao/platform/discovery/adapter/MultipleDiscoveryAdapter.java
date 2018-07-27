package com.yidao.platform.discovery.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.discovery.bean.FriendsNewsBean;

import java.util.List;

@Deprecated
public class MultipleDiscoveryAdapter extends BaseMultiItemQuickAdapter<FriendsNewsBean, BaseViewHolder> {

    private Context mContext;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleDiscoveryAdapter(Context context,List<FriendsNewsBean> data) {
        super(data);
        mContext = context;
        addItemType(FriendsNewsBean.ITEM_ONE, R.layout.discovery_friends_item);
        addItemType(FriendsNewsBean.ITEM_TWO, R.layout.discovery_friends_item);
        addItemType(FriendsNewsBean.ITEM_THREE, R.layout.discovery_friends_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendsNewsBean item) {
        switch (item.getItemType()) {
            case FriendsNewsBean.ITEM_ONE:
                Glide.with(mContext).load(ContextCompat.getDrawable(mContext,R.drawable.mypic)).into((ImageView) helper.getView(R.id.iv_discovery_icon));
                helper.setText(R.id.tv_discovery_name,"xhl");
                helper.setText(R.id.tv_discovery_time,"11:39");
                helper.setText(R.id.tv_discovery_vote,"10001");
                helper.setText(R.id.tv_discovery_content,"杀菌付款后多少空间划分");
                break;
            case FriendsNewsBean.ITEM_TWO:
                break;
            case FriendsNewsBean.ITEM_THREE:
                break;
        }
    }
}
