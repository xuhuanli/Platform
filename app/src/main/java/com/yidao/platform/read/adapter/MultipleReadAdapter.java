package com.yidao.platform.read.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.utils.ScreenUtil;
import com.yidao.platform.read.bean.ReadNewsBean;
import com.yidao.platform.read.presenter.ReadFragmentPresenter;
import com.yidao.platform.read.view.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.view.BannerViewPager;

import java.util.List;

public class MultipleReadAdapter extends BaseMultiItemQuickAdapter<ReadNewsBean, BaseViewHolder> {
    private List<ReadNewsBean> data;
    private Context mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleReadAdapter(Context context, List<ReadNewsBean> data) {
        super(data);
        this.data = data;
        this.mContext = context;
        addItemType(ReadNewsBean.ITEM_ONE, R.layout.read_mainpage_big_image);
        addItemType(ReadNewsBean.ITEM_TWO, R.layout.read_mainpage_text_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadNewsBean item) {
        switch (item.getItemType()) {
            case ReadNewsBean.ITEM_ONE:
                if (item.getCategoryId() == Constant.CATEGORY_ID_FUHUA) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.fuhua);
                } else if (item.getCategoryId() == Constant.CATEGORY_ID_HULIANGWANG) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.hulianwang);
                } else if (item.getCategoryId() == Constant.CATEGORY_ID_ZIXUN) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.chanyezixun);
                } else if (item.getCategoryId() == Constant.CATEGORY_ID_ZHENGCE) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.zhengce);
                } else if (item.getCategoryId() == Constant.CATEGORY_ID_TOUZI) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.touzi);
                } else if (item.getCategoryId() == Constant.CATEGORY_ID_BP) {
                    helper.setImageResource(R.id.iv_item_name, R.drawable.bp);
                }
                Glide.with(mContext).load(R.drawable.mypic).into((ImageView) helper.getView(R.id.iv_big_image));
                helper.setText(R.id.tv_item_title, item.getTitle());
                helper.addOnClickListener(R.id.tv_item_more);
                break;
            case ReadNewsBean.ITEM_TWO:
                helper.setText(R.id.read_list_content, "状态栏布局和图标挺像Android,但是这白底黑字Android设计规范里可没有啊，于是我们开发的时候果断忽视这个状态栏了（当时大部分用户")
                        .setText(R.id.tv_read_count, "12121阅读")
                        .setText(R.id.tv_news_time, "6小时前");
                Glide.with(mContext).load(R.drawable.mypic).into((ImageView) helper.getView(R.id.read_list_image));
                break;
        }
    }
}
