package com.yidao.platform.app.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtil {
    /**
     * 根据手机分辨率将dp转为px单位
     */
    public static int dip2px(Context mContext, float dpValue) {
        final float scale = mContext.getResources()
                .getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context mContext, float pxValue) {
        final float scale = mContext.getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 屏幕宽高
     *
     * @param mContext 上下文
     * @return
     */
    private static int[] getScreenSize(Context mContext) {
        DisplayMetrics dm = mContext
                .getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        return new int[]{screenWidth, screenHeight};
    }

    /**
     * 获取手机屏幕的宽度
     *
     * @param mContext 上下文
     * @return
     */
    public static int getScreenWidth(Context mContext) {
        int screen[] = getScreenSize(mContext);
        return screen[0];
    }

    /**
     * 获取手机屏幕的高度
     *
     * @param mContext 上下文
     * @return
     */
    public static int getScreenHeight(Context mContext) {
        int screen[] = getScreenSize(mContext);
        return screen[1];
    }
}