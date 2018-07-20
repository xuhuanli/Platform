package com.yidao.platform.info.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

public class CollectionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CollectionAdapter(@Nullable List<String> data) {
        super(R.layout.item_my_collection, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_collection_title,"收藏标题");
    }
}
