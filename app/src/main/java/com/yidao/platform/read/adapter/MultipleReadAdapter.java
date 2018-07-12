package com.yidao.platform.read.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

public class MultipleReadAdapter extends BaseMultiItemQuickAdapter<ReadNewsBean, BaseViewHolder> {
    private List<ReadNewsBean> data;
    private Context mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleReadAdapter(Context context, List<ReadNewsBean> data) {
        super(data);
        this.data = data;
        this.mContext = context;
        addItemType(ReadNewsBean.ITEM_ONE, R.layout.read_mainpage_pure_text);
        addItemType(ReadNewsBean.ITEM_TWO, R.layout.read_mainpage_text_image);
        addItemType(ReadNewsBean.ITEM_THREE, R.layout.read_mainpage_text_bigimage);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadNewsBean item) {
        switch (item.getItemType()) {
            case ReadNewsBean.ITEM_ONE:
                Glide.with(mContext).load(ContextCompat.getDrawable(mContext, R.drawable.mypic)).into((ImageView) helper.getView(R.id.iv_discovery_icon));
                helper.setText(R.id.tv_discovery_name, "xhl");
                helper.setText(R.id.tv_discovery_content, "这是内容");
                helper.addOnClickListener(R.id.tv_discovery_reply);
                break;
            case ReadNewsBean.ITEM_TWO:
                helper.setText(R.id.read_list_content, "这是内容");
                helper.setText(R.id.read_list_sum, "6324");
                helper.setText(R.id.read_list_time, "2018-7-9");
                Glide.with(mContext).load(ContextCompat.getDrawable(mContext, R.drawable.a)).into((ImageView) helper.getView(R.id.read_list_image));
                break;
            case ReadNewsBean.ITEM_THREE:
                Glide.with(mContext).load(ContextCompat.getDrawable(mContext, R.drawable.a)).into((ImageView) helper.getView(R.id.iv_big_pic));
                break;
        }
    }
}
