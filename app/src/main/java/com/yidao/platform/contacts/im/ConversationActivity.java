package com.yidao.platform.contacts.im;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.contacts.ContactsSettingActivity;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * 会话界面
 */
public class ConversationActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener,
        View.OnClickListener,
        IViewConversation {
    private static final String TAG = "ConversationActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.ll_list)
    LinearLayout llList;
    @BindView(R.id.tv_icon1)
    TextView tvIcon1;
    @BindView(R.id.tv_icon2)
    TextView tvIcon2;
    @BindView(R.id.tv_icon3)
    TextView tvIcon3;
    @BindView(R.id.tv_icon4)
    TextView tvIcon4;
    @BindView(R.id.tv_icon5)
    TextView tvIcon5;
    private String imToken;
    private String userId;
    private ConversationPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imToken = IPreference.prefHolder.getPreference(this).get(Constant.IM_TOKEN, IPreference.DataType.STRING);
        userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        mPresenter = new ConversationPresenter(this);
        initToolbar();
        initListener();
        if (RongIM.getInstance().getCurrentConnectionStatus().getValue() != RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED.getValue()) {
            MyLogger.e("没有连接server");
            connectToIM(imToken, userId);
        }
    }

    private void connectToIM(String token, String id) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //request new token from app server
                mPresenter.requestIMToken(id, 1);
            }

            @Override
            public void onSuccess(String s) {
                //s equals userId
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    private void initListener() {
        tvIcon1.setOnClickListener(this);
        tvIcon2.setOnClickListener(this);
        tvIcon3.setOnClickListener(this);
        tvIcon4.setOnClickListener(this);
        tvIcon5.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_conversation;
    }

    private void initToolbar() {
        String targetId = getIntent().getData().getQueryParameter("targetId");
        String titleStr = getIntent().getData().getQueryParameter("title");//需要实现用户提供者，否者没有昵称
        tbTitle.setText(!TextUtils.isEmpty(titleStr) ? titleStr : "未知用户");
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.inflateMenu(R.menu.conversation_menu);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.conversation_menu_more:
                Intent intent = new Intent(this, ContactsSettingActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_icon1:
                AlertDialog.Builder builder = creatDialog("xxx的电话号码", "18866668888");
                builder.show();
                break;
            case R.id.tv_icon2:
                AlertDialog.Builder dialog = creatDialog("xxxx的微信号码", "cdq");
                dialog.show();
                break;
            case R.id.tv_icon3: //测试自定义消息
                RongIM.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, "888888", new CustomizeMessage(), "这是自定义消息", "", new RongIMClient.SendImageMessageCallback() {
                    @Override
                    public void onAttached(Message message) {

                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {

                    }

                    @Override
                    public void onSuccess(Message message) {

                    }

                    @Override
                    public void onProgress(Message message, int i) {

                    }
                });
                break;
            case R.id.tv_icon4:
                break;
            case R.id.tv_icon5:
                break;
        }
    }

    private AlertDialog.Builder creatDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("复制", (dialog, which) -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("", message);
            assert clipboardManager != null;
            clipboardManager.setPrimaryClip(clipData);
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        return builder;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void requestIMTokenSuccess(String token) {
        //save token
        IPreference.prefHolder.getPreference(this).put(Constant.IM_TOKEN, token);
        connectToIM(token, userId);
    }
}
