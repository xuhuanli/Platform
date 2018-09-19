package com.yidao.platform.read.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.utils.ConvertFormatUtil;
import com.yidao.platform.app.utils.TimeUtil;
import com.yidao.platform.read.bean.ReadNewsBean;

import java.util.List;

public class MultipleReadAdapter extends BaseMultiItemQuickAdapter<ReadNewsBean, BaseViewHolder> {
    private Context mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleReadAdapter(Context context, List<ReadNewsBean> data) {
        super(data);
        this.mContext = context;
        addItemType(ReadNewsBean.ITEM_ONE, R.layout.read_mainpage_big_image);
        addItemType(ReadNewsBean.ITEM_TWO, R.layout.read_mainpage_text_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadNewsBean item) {
        switch (item.getItemType()) {
            case ReadNewsBean.ITEM_ONE:
                if (item.getCategoryId() == Constant.CATEGORY_ID_LABEL_1) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.label1);
                } else if (item.getCategoryId() == Constant.CATEGORY_ID_LABEL_2) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.label2);
                } else if (item.getCategoryId() == Constant.CATEGORY_ID_LABEL_3) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.label3);
                } else if (item.getCategoryId() == Constant.CATEGORY_ID_LABEL_4) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.label4);
                } else if (item.getCategoryId() == Constant.CATEGORY_ID_LABEL_5) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.label5);
                } else if (item.getCategoryId() == Constant.CATEGORY_ID_LABEL_6) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.label6);
                } else if (item.getCategoryId() == Constant.CATEGORY_ID_LABEL_7) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.label7);
                }
                ImageView imageView = helper.getView(R.id.iv_big_image);
                imageView.setClipToOutline(true);
                Glide.with(mContext).load(item.getHomeImg()).apply(new RequestOptions().placeholder(R.drawable.info_head_p).error(R.drawable.info_head_p)).into(imageView);
                helper.setText(R.id.tv_item_title, item.getTitle());
                helper.addOnClickListener(R.id.tv_item_more);
                break;
            case ReadNewsBean.ITEM_TWO:
                String deployTime = item.getDeployTime();
                String timeSamp = null;
                timeSamp = TextUtils.equals(deployTime, Constant.STRING_RECENT) ? deployTime : TimeUtil.fromToday(Long.parseLong(deployTime));
                helper.setText(R.id.read_list_content, item.getTitle())
                        .setText(R.id.tv_read_count, ConvertFormatUtil.convertCount(item.getReadAmount()))
                        .setText(R.id.tv_news_time, timeSamp);
                ImageView smallView = helper.getView(R.id.read_list_image);
                smallView.setClipToOutline(true);
                Glide.with(mContext).load(item.getHomeImg()).apply(new RequestOptions().placeholder(R.drawable.info_head_p).error(R.drawable.info_head_p)).into(smallView);
                break;
        }
    }
}
