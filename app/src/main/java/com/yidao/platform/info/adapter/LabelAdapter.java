package com.yidao.platform.info.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.info.model.TagBean;

import java.util.List;

import static com.umeng.socialize.utils.DeviceConfig.context;

public class LabelAdapter extends BaseQuickAdapter<TagBean, BaseViewHolder> {

  private   List<TagBean> data;
    private  Context context;

    public LabelAdapter(@Nullable List<TagBean> data, Context context) {
        super(R.layout.info_select_label_item, data);
        this.data =data;
        this.context =context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TagBean item) {
        Boolean isselected = item.getIsselected();
        helper.setText(R.id.tv_label, item.getName());
        helper.addOnClickListener(R.id.tv_label);
       TextView v =  (TextView)helper.itemView;
        if (isselected) {
            v.setSelected(true);
            v.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else {
            v.setSelected(false);
            v.setTextColor(context.getResources().getColor(R.color.FF999999));
        }


    }
}
