package com.yidao.platform.info.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.List;

public class SystemViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SystemViewAdapter(@Nullable List<String> data) {
        super(R.layout.info_message_system_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_system,"这是系统的消息内容部分");
    }
}
