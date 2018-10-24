package com.yidao.platform.contacts.im;

import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.view.ViewGroup;

import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.MessageContent;

public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider {
    @Override
    public void bindView(View view, int i, MessageContent messageContent, UIMessage uiMessage) {

    }

    @Override
    public Spannable getContentSummary(MessageContent messageContent) {
        return null;
    }

    @Override
    public void onItemClick(View view, int i, MessageContent messageContent, UIMessage uiMessage) {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void bindView(View view, int i, Object o) {

    }
}
