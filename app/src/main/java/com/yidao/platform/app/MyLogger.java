package com.yidao.platform.app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.yidao.platform.app.Constant;

/**
 * The type My logger.
 *
 * @author xuhuanli
 */
public class MyLogger {

    /**
     * Init logger.
     */
    public static void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return Constant.IS_DEBUG;
            }
        });
    }

    /**
     * .
     *
     * @param message the message
     */
    public static void i(@NonNull String message) {
        Logger.i(message);
    }

    /**
     * .
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void i(String tag, @NonNull String message) {
        Logger.i(tag, message);
    }

    /**
     * D.
     *
     * @param message the message
     */
    public static void d(@NonNull String message) {
        Logger.d(message);
    }

    /**
     * W.
     *
     * @param message the message
     */
    public static void w(@NonNull String message) {
        Logger.w(message);
    }

    /**
     * E.
     *
     * @param message the message
     */
    public static void e(@NonNull String message) {
        Logger.e(message);
    }
}
