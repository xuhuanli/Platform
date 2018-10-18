package com.yidao.platform.contacts;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseFragment;

import butterknife.BindView;

public class ContactsFragment extends BaseFragment {

    @BindView(R.id.tb_include)
    Toolbar toolbar;

    @Override
    protected void initView() {
        initToolbar();
    }

    private void initToolbar() {
        TextView title = toolbar.findViewById(R.id.tb_title);
        toolbar.inflateMenu(R.menu.contacts_menu);
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
