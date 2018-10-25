package com.yidao.platform.contacts.im;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.contacts.ContactsMainPageActivity;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

public class IMListenerWrapper implements RongIM.OnSendMessageListener,
        RongIMClient.OnReceiveMessageListener,
        RongIMClient.ConnectionStatusListener,
        RongIM.ConversationClickListener,
        RongIM.ConversationListBehaviorListener {
    private static final String TAG = "IMListenerWrapper";

    private static List<Friend> list;
    private static IMListenerWrapper sInstance;

    private IMListenerWrapper(Context context) {
        //initListener();
        RongIM.getInstance().setSendMessageListener(this);
        RongIM.setOnReceiveMessageListener(this);
        RongIM.setConversationClickListener(this);
        RongIM.setConversationListBehaviorListener(this);
        RongIM.setConnectionStatusListener(this);
        setInputProvider();
    }

    private void initListener() {
        /*list = new ArrayList<>();
        list.add(new Friend("10001", "xuhuanli", "http://www.51zxw.net/bbs/UploadFile/2013-4/201341122335711220.jpg"));
        list.add(new Friend("123456789", "cdq", "http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"));
        RongIM.setUserInfoProvider(this, true);*/
    }

    public static void init(Context context) {
        if (sInstance == null) {
            synchronized (IMListenerWrapper.class) {
                if (sInstance == null) {
                    sInstance = new IMListenerWrapper(context);
                }
            }
        }
    }

    /**
     * 获取RongCloud 实例。
     *
     * @return RongCloud。
     */
    public static IMListenerWrapper getInstance() {
        return sInstance;
    }


    private void setInputProvider() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new IMExtensionModule());
            }
        }
    }

    //---------------用户信息提供者---------------------//
/*
    @Override
    public UserInfo getUserInfo(String id) {
        for (Friend i : list) {
            if (i.getUserId().equals(id)) {
                Log.e("TAG", i.getPortraitUri());
                return new UserInfo(i.getUserId(), i.getUserName(), Uri.parse(i.getPortraitUri()));
            }
        }
        Log.e("MainActivity", "UserId is ：" + id);
        return null;
    }*/

    //------------------消息发送监听--------------------//

    @Override
    public Message onSend(Message message) {  //加工完message后把消息return出去
        return message;
    }

    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {

        if (message.getSentStatus() == Message.SentStatus.FAILED) {
            if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_CHATROOM) {
                //不在聊天室
            } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION) {
                //不在讨论组
            } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_GROUP) {
                //不在群组
            } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST) {
                //你在他的黑名单中
            }
        }

        MessageContent messageContent = message.getContent();

        if (messageContent instanceof TextMessage) {//文本消息
            TextMessage textMessage = (TextMessage) messageContent;
            MyLogger.e("onSent-TextMessage:" + textMessage.getContent());
        } else if (messageContent instanceof ImageMessage) {//图片消息
            ImageMessage imageMessage = (ImageMessage) messageContent;
            MyLogger.e("onSent-ImageMessage:" + imageMessage.getRemoteUri());
        } else if (messageContent instanceof VoiceMessage) {//语音消息
            VoiceMessage voiceMessage = (VoiceMessage) messageContent;
            MyLogger.e("onSent-voiceMessage:" + voiceMessage.getUri().toString());
        } else if (messageContent instanceof RichContentMessage) {//图文消息
            RichContentMessage richContentMessage = (RichContentMessage) messageContent;
            MyLogger.e("onSent-RichContentMessage:" + richContentMessage.getContent());
        } else {
            MyLogger.e("onSent-其他消息，自己来判断处理");
        }
        return false;
    }

    //-------------接收到消息监听---------------//

    @Override
    public boolean onReceived(Message message, int i) {
        MyLogger.e(" 收到消息的处理。");
        return false;
    }

    //-------------网络监听---------------//

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        switch (connectionStatus) {
            case NETWORK_UNAVAILABLE://网络不可用。
                break;
            case CONNECTED://连接成功。
                break;
            case CONNECTING://连接中。
                break;
            case DISCONNECTED://断开连接。
                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                break;
            case TOKEN_INCORRECT://token invalid
                //从app server去重新获取
                break;
            case SERVER_INVALID: //server invalid
                break;
            case CONN_USER_BLOCKED: //in blacklist
                break;
        }
    }


    //-------------会话界面的行为操作回调---------------//

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
        Toast.makeText(context, "头像单击", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, ContactsMainPageActivity.class);
        context.startActivity(intent);
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
        Toast.makeText(context, "头像长按", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        MyLogger.e("message clicked!");
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s, Message message) {
        Toast.makeText(context, "消息连接点击", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        Toast.makeText(context, "消息长按", Toast.LENGTH_SHORT).show();
        return false;
    }

    //-------------列表界面的行为操作回调---------------//

    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }
}
