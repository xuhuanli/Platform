package com.xuhuanli.httplibrary.utils;

import android.widget.Toast;

import com.xuhuanli.httplibrary.MyRetrofit;

public class ToastUtils {

    private static Toast mToast;

    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(MyRetrofit.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
