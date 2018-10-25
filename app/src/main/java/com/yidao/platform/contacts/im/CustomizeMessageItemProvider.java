package com.yidao.platform.contacts.im;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.utils.MyLogger;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

@ProviderTag(messageContent = CustomizeMessage.class)
public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomizeMessage> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_accept:
                TextMessage textMessage = TextMessage.obtain("我的手机号是18268886666");
                Message message = Message.obtain("666666", Conversation.ConversationType.PRIVATE, textMessage);
                RongIM.getInstance().sendMessage(message, "对方向您发送了手机号", "", new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        MyLogger.e("消息本地数据库存储成功的回调");
                    }

                    @Override
                    public void onSuccess(Message message) {
                        MyLogger.e("消息通过网络发送成功的回调");
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        MyLogger.e("消息发送失败的回调");
                    }
                });
                break;
            case R.id.tv_refuse:
                TextMessage refuseMessage = TextMessage.obtain("我拒绝了您的请求,余额将稍后退回到您的钱包");
                Message refuseMsg = Message.obtain("666666", Conversation.ConversationType.PRIVATE, refuseMessage);
                RongIM.getInstance().sendMessage(refuseMsg, "对方拒绝了您的请求", "", new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        MyLogger.e("refuseMessage消息本地数据库存储成功的回调");
                    }

                    @Override
                    public void onSuccess(Message message) {
                        MyLogger.e("refuseMessage消息通过网络发送成功的回调");
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        MyLogger.e("refuseMessage消息发送失败的回调");
                    }
                });
                break;
        }
    }

    class ViewHolder {
        TextView message;
        TextView accept;
        TextView refuse;
        ConstraintLayout clButtons;
        ConstraintLayout clCustomMsg;
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_message, viewGroup, false);
        ViewHolder holder = new ViewHolder();
        holder.message = view.findViewById(R.id.tv_message_content);
        holder.clButtons = view.findViewById(R.id.cl_buttons);
        holder.accept = view.findViewById(R.id.tv_accept);
        holder.refuse = view.findViewById(R.id.tv_refuse);
        holder.clCustomMsg = view.findViewById(R.id.cl_custom_msg);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.clButtons.setVisibility(View.GONE);
            holder.message.setText("已向对方支付5元诚意金,申请交换手机号码");
            GradientDrawable background = (GradientDrawable) holder.clCustomMsg.getBackground();
            background.setColor(Color.parseColor("#ffc7e2ff"));
        } else {
            holder.clButtons.setVisibility(View.VISIBLE);
            holder.message.setText("对方向您提供5元诚意金 申请交换手机号码");
            holder.accept.setOnClickListener(this);
            holder.refuse.setOnClickListener(this);
        }
    }

    @Override
    public Spannable getContentSummary(CustomizeMessage customizeMessage) {
        return new SpannableString("对方向您发起了打赏/希望能够取得电话/微信号");
    }

    @Override
    public void onItemClick(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {
        MyLogger.e("onItemClick");
    }
}
