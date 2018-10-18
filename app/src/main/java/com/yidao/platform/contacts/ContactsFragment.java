package com.yidao.platform.contacts;

import android.view.Menu;
import android.view.MenuInflater;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseFragment;

public class ContactsFragment extends BaseFragment {

    @Override
    protected void initView() {
        initToolbar();
    }

    private void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.contacts_fragment_content;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contacts_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
