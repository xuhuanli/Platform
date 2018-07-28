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
        //自定义View没有复用 存在卡顿现象
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.bp_apply);
    }

    @Override
    protected int getLayoutId() {
        //Deprecated 旧版
        //return R.layout.service_fragment_bp;
        return R.layout.service_fragment_bp_new;
    }


    @Override
    protected void initData() {

    }
}
