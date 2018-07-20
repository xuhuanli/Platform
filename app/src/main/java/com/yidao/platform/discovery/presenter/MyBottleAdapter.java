package com.yidao.platform.discovery.presenter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

public class MyBottleAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public MyBottleAdapter(@Nullable List<String> data) {
        super(R.layout.my_bottle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setImageResource(R.id.my_bootle_icon,R.drawable.mypic)
                .setText(R.id.my_bottle_location,"杭州")
                .setText(R.id.my_bottle_content,"嗨呀")
                .setText(R.id.my_bottle_time,"14:21");
    }
}
