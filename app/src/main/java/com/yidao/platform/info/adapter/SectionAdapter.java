package com.yidao.platform.info.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.info.model.SettingsSection;
import com.yidao.platform.info.view.CustomTextView;
import com.yidao.platform.info.view.NewSettingsActivity;

import java.util.List;

public class SectionAdapter extends BaseSectionQuickAdapter<SettingsSection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SettingsSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SettingsSection item) {

    }

    @Override
    protected void convert(BaseViewHolder helper, SettingsSection item) {
        CustomTextView view = helper.getView(R.id.customTextView);
        switch (helper.getLayoutPosition()) {
            case 1:
                view.setKey("头像");
                view.setImageView("http://www.51zxw.net/bbs/UploadFile/2013-4/201341122335711220.jpg", true);
                break;
            case 2:
                setKeyAndValue(view, "昵称", "陈代权陈代权陈代权陈代权");
                break;
            case 3:
                setKeyAndValue(view, "手机号码", "18888886666");
                break;
            case 5:
                setKeyAndValue(view, "简介", "");
                break;
            case 6:
                setKeyAndValue(view, "地区", "");
                break;
            case 7:
                setKeyAndValue(view, "所属公司", "");
                break;
            case 8:
                setKeyAndValue(view, "职业标签", "");
                break;
            case 10:
                setKeyAndValue(view, "黑名单", "");
                break;
            case 11:
                setKeyAndValue(view, "关于我们", "");
                break;
            case 12:
                setKeyAndValue(view, "清除缓存", "56K");
                break;
            case 14:
                view.removeAllViewsInLayout();
                View footer = LayoutInflater.from(view.getContext()).inflate(R.layout.sign_up, view, false);
                view.addView(footer);
                break;
        }
    }

    private void setKeyAndValue(CustomTextView view, String key, String value) {
        view.setKey(key);
        view.setValue(value);
    }
}
