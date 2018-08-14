package com.yidao.platform.read.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ErrorAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ErrorAdapter(@Nullable List<String> data) {
        super(0, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
