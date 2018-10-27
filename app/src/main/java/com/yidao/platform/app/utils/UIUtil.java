package com.yidao.platform.app.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.info.adapter.LabelAdapter;
import com.yidao.platform.info.model.TagBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UIUtil {

    /**
     * 初始化 标签
     *
     * @param recyclerview
     * @param context
     */
    public static void initRecyclerView(RecyclerView recyclerview, Context context, ArrayList<TagBean> seleteds, OnTagItemClickListener mOnTagItemClickListener) {

        recyclerview.setLayoutManager(new GridLayoutManager(context, 5));
        ArrayList<TagBean> list = new ArrayList<>();
        Map<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("应聘", false);
        hashMap.put("社交", false);
        hashMap.put("合作", false);
        hashMap.put("融资", false);
        hashMap.put("合伙", false);
        int seletedsize = 0;
        if (seleteds != null) {
            for (int i = 0; i < seleteds.size(); i++) {
                hashMap.put(seleteds.get(i).getName(), seleteds.get(i).getIsselected());
            }
        }
        for (String key : hashMap.keySet()) {
            list.add(new TagBean(key, hashMap.get(key)));
            if (hashMap.get(key)) {
                seletedsize++;
            }
        }
        LabelAdapter labelAdapter = new LabelAdapter(list, context);
        recyclerview.setAdapter(labelAdapter);

        if (mOnTagItemClickListener != null) {
            mOnTagItemClickListener.onItemClick(list.size(), seletedsize);
        }
        int finalSeletedsize = seletedsize;
        labelAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TextView label = (TextView) view;
            int seletedsize1 = finalSeletedsize;
            boolean selected = label.isSelected();
            if (!selected) {
                label.setSelected(true);
                label.setTextColor(context.getResources().getColor(R.color.colorWhite));
                seletedsize1++;
            } else {
                label.setSelected(false);
                label.setTextColor(context.getResources().getColor(R.color.FF999999));
                seletedsize1--;
            }
            if (mOnTagItemClickListener != null) {
                mOnTagItemClickListener.onItemClick(list.size(), seletedsize1);
            }

        });
    }


    public interface OnTagItemClickListener {
        void onItemClick(int totalsize, int seletedsize);
    }
}