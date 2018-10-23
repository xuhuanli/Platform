package com.yidao.platform.contacts.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.contacts.ContactsSettingActivity;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 会话界面
 */
public class ConversationActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    //    public static final String IM_TOKEN = "pqGbqEDBlbzfuLOQ65dubKHE8cj6DC6fvsLruSyjeJf7Xxd2EkTV4F4bx3cmRlXQCWPjtPql/xhiHuQ9oW64lA=="; //xuhuanli 666666
    public static final String IM_TOKEN = "ogj/f7uqNuileVQQwp6cN65gJP9dw5UXqOdf15NyuAhFZphMr71MOrTcQLVeYki3c1U/ryEACiYU6Ptin/yhWw=="; //徐焕利 888888
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
    private boolean isConnect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        if (RongIM.getInstance().getCurrentConnectionStatus().getValue() != RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED.getValue()) {
            MyLogger.e("没有连接server");
            RongIM.connect(IM_TOKEN, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    MyLogger.e("onTokenIncorrect");
                }

                @Override
                public void onSuccess(String s) {
                    isConnect = true;
                    MyLogger.e("onSuccess userId is " + s);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
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
}
