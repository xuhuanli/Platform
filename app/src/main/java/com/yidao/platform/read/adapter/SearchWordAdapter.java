package com.yidao.platform.read.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

public class SearchWordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    List<String> data;

    public SearchWordAdapter(@Nullable List<String> data) {
        super(R.layout.read_search_words, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_search_word,item);
    }
}
