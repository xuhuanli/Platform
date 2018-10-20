package com.yidao.platform.contacts.im;

import android.content.Context;
import android.util.Log;

import com.yidao.platform.app.utils.MyLogger;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class IMNotificationReceiver extends PushMessageReceiver {
    private static final String TAG = "DemoNotificationReceive";
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        MyLogger.e("Arrived");
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        MyLogger.e("clicked");
        return false;
    }
}
