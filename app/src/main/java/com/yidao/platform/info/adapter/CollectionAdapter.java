package com.yidao.platform.info.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.app.utils.ConvertFormatUtil;
import com.yidao.platform.read.bean.ReadNewsBean;

import java.util.List;

public class CollectionAdapter extends BaseQuickAdapter<ReadNewsBean, BaseViewHolder> {
    public CollectionAdapter(@Nullable List<ReadNewsBean> data) {
        super(R.layout.item_my_collection, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadNewsBean item) {
        String time = item.getDeployTime();
        helper.setText(R.id.tv_collection_title, item.getTitle())
        .setText(R.id.textView,time);
    }
}
