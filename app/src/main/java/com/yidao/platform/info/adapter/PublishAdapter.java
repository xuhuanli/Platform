package com.yidao.platform.info.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

public class PublishAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PublishAdapter(@Nullable List<String> data) {
        super(R.layout.item_my_publish, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setImageResource(R.id.iv_discovery_icon, R.drawable.info_head_p)
                .setText(R.id.tv_discovery_name, "xhl")
                .setText(R.id.tv_discovery_time, "16:47")
                .setText(R.id.tv_discovery_vote, "233")
                .setText(R.id.tv_discovery_content, "撒娇的卡就会看到发生口角啊")
                .setGone(R.id.tv_delete, true)
                .addOnClickListener(R.id.tv_discovery_reply)
                .addOnClickListener(R.id.tv_delete);
        BGANinePhotoLayout ninePhotoLayout = (BGANinePhotoLayout) helper.getView(R.id.npl_item_moment_photos);
        ArrayList<String> dataList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            dataList.add("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered1.png");
        }
        ninePhotoLayout.setData(dataList);
    }
}
