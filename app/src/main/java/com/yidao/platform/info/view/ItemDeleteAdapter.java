package com.yidao.platform.info.view;

import android.support.annotation.Nullable;
import android.view.View;

import com.allen.library.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

public class ItemDeleteAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ItemDeleteAdapter(@Nullable List<String> data) {
        super(R.layout.item_rv_swipemenu, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.getView(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("delete item clicked");
            }
        });
    }
}
