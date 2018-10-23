package com.yidao.platform.contacts.im;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.contacts.ContactsSettingActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 会话界面
 */
public class ConversationActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
//    public static final String IM_TOKEN = "pqGbqEDBlbzfuLOQ65dubKHE8cj6DC6fvsLruSyjeJf7Xxd2EkTV4F4bx3cmRlXQCWPjtPql/xhiHuQ9oW64lA=="; //xuhuanli 666666
    public static final String IM_TOKEN = "ogj/f7uqNuileVQQwp6cN65gJP9dw5UXqOdf15NyuAhFZphMr71MOrTcQLVeYki3c1U/ryEACiYU6Ptin/yhWw=="; //徐焕利 888888
    private static final String TAG = "ConversationActivity";
    private boolean isConnect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_conversation);
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

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        String targetId = getIntent().getData().getQueryParameter("targetId");
        String titleStr = getIntent().getData().getQueryParameter("title");//需要实现用户提供者，否者没有昵称
        TextView title = toolbar.findViewById(R.id.tb_title);
        title.setText(!TextUtils.isEmpty(titleStr) ? titleStr : "未知用户");
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
