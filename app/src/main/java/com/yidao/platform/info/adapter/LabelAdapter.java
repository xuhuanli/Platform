package com.yidao.platform.info.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

public class LabelAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public LabelAdapter(@Nullable List<String> data) {
        super(R.layout.info_select_label_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_label, item);
        helper.addOnClickListener(R.id.tv_label);
    }
}
