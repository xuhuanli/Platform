package com.yidao.platform.contacts.im;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.contacts.ContactsSearchActivity;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.model.UserInfo;

/**
 * 会话列表
 */
public class ConversationListActivity extends BaseActivity implements RongIM.UserInfoProvider, IUnReadMessageObserver, Toolbar.OnMenuItemClickListener {
    private static List<Friend> list;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        list = new ArrayList<>();
        list.add(new Friend("666666", "xuhuanli", "http://www.51zxw.net/bbs/UploadFile/2013-4/201341122335711220.jpg"));
        list.add(new Friend("888888", "徐焕利", "http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"));
        RongIM.setUserInfoProvider(this, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_conversation_list;
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(v -> finish());
        ((TextView) mToolbar.findViewById(R.id.tb_title)).setText(R.string.friends);

        mToolbar.inflateMenu(R.menu.conversation_list_menu);
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public UserInfo getUserInfo(String id) {
        for (Friend i : list) {
            if (i.getUserId().equals(id)) {
                MyLogger.e(i.getPortraitUri());
                return new UserInfo(i.getUserId(), i.getUserName(), Uri.parse(i.getPortraitUri()));
            }
        }
        return null;
    }

    @Override
    public void onCountChanged(int i) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_icon:
                Intent intent = new Intent(this, ContactsSearchActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}
