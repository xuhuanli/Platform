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
import java.util.List;
import java.util.Map;

public class UIUtil {

    /**
     * 初始化 标签
     *
     * @param recyclerview
     * @param context
     */
    public static void initRecyclerView(RecyclerView recyclerview, Context context, List selec, TextView tv_count) {

        recyclerview.setLayoutManager(new GridLayoutManager(context, 5));
        ArrayList<TagBean> list = new ArrayList<>();
        ArrayList<TagBean> localList = new ArrayList<>();
        Map<String, Boolean> hashMap = new HashMap<String, Boolean>();
        localList.add(new TagBean("应聘", false,1));
        localList.add(new TagBean("社交", false,2));
        localList.add(new TagBean("合作", false,3));
        localList.add(new TagBean("融资", false,4));
        localList.add(new TagBean("合伙", false,5));
        beginNumber = 0;
        LabelAdapter labelAdapter = new LabelAdapter(list, context);
        recyclerview.setAdapter(labelAdapter);

        if (tv_count != null) {
            tv_count.setText(beginNumber + "/" + list.size());
        }
        labelAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TextView label = (TextView) view;
            boolean selected = label.isSelected();
            if (!selected) {
                label.setSelected(true);
                label.setTextColor(context.getResources().getColor(R.color.colorWhite));
                beginNumber++;
                if (selec != null) {
                    selec.add(localList.get(position).getId());
                }
            } else {
                label.setSelected(false);
                label.setTextColor(context.getResources().getColor(R.color.FF999999));
                beginNumber--;
                selec.remove(localList.get(position).getId());
            }
            if (tv_count != null) {
                tv_count.setText(beginNumber + "/" + list.size());
            }
        });
    }

    public static int beginNumber = 0;


    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 屏幕的高
     *
     * @param context
     * @return
     */
    public static final int getHeightInPx(Context context) {
        final int height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }
}