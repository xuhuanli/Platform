package com.yidao.platform.receiver;

import android.content.Context;
import android.content.Intent;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.info.view.InfoMyMessageActivity;

import java.util.Map;

/**
 * aliyu push 广播接收处理
 *
 * @author yuxinwei
 */
public class AliyunPushReceiver extends MessageReceiver {

    /**
     * 消息接收部分的LOG_TAG
     */
    public static final String REC_TAG = "aliyun_push_receiver";

    /**
     * 推送通知的回调方法
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
    }

    /**
     * 应用处于前台时通知到达回调。注意:该方法仅对自定义样式通知有效,
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     * @param openType
     * @param openActivity
     * @param openUrl
     */
    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        MyLogger.i(REC_TAG, "onNotificationReceivedInApp ： " + " : " + title + " : " + summary + "  " + extraMap + " : " + openType + " : " + openActivity + " : " + openUrl);
    }

    /**
     * 推送消息的回调方法
     *
     * @param context
     * @param cPushMessage
     */
    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        MyLogger.i(REC_TAG, "收到一条推送消息 ： " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
    }

    /**
     * 从通知栏打开通知的扩展处理
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Intent intent = new Intent(context, InfoMyMessageActivity.class);
        context.startActivity(intent);
    }

    /**
     * 通知删除回调
     *
     * @param context
     * @param messageId
     */
    @Override
    public void onNotificationRemoved(Context context, String messageId) {
        MyLogger.i(REC_TAG, "onNotificationRemoved ： " + messageId);
    }

    /**
     * 无动作通知点击回调。当在后台或阿里云控制台指定的通知动作为无逻辑跳转时,通知点击回调为onNotificationClickedWithNoAction而不是onNotificationOpened
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        MyLogger.i(REC_TAG, "onNotificationClickedWithNoAction ： " + " : " + title + " : " + summary + " : " + extraMap);
    }
}
