package com.yidao.platform.contacts.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.contacts.ContactsSearchActivity;

import io.rong.imkit.manager.IUnReadMessageObserver;

/**
 * 会话列表
 */
public class ConversationListActivity extends BaseActivity implements IUnReadMessageObserver, Toolbar.OnMenuItemClickListener {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
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
