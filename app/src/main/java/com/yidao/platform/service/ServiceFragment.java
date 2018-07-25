package com.yidao.platform.service;

import android.support.v7.widget.Toolbar;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseFragment;

import butterknife.BindView;

public class ServiceFragment extends BaseFragment {

    @BindView(R.id.toolbar_service)
    Toolbar mToolbar;
    @Override
    protected void initView() {
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.bp_apply);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.service_fragment_bp;
    }

    @Override
    protected void initData() {

    }
}