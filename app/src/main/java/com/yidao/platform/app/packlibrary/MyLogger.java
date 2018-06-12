package com.yidao.platform.app.packlibrary;

import android.support.annotation.NonNull;

import com.orhanobut.logger.AndroidLogAdapter;

public class MyLogger {

    public static void initLogger() {
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static void d(@NonNull String message) {
        com.orhanobut.logger.Logger.d(message);
    }

    public static void w(@NonNull String message) {
        com.orhanobut.logger.Logger.w(message);
    }

    public static void e(@NonNull String message) {
        com.orhanobut.logger.Logger.e(message);
    }
}
