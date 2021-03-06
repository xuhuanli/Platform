package com.yidao.platform.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;

import butterknife.BindView;

public class ProtocolActivity extends BaseActivity {
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;
    @BindView(R.id.toolbar_info)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvProtocol.setMovementMethod(new ScrollingMovementMethod());
        addDisposable(RxToolbar.navigationClicks(mToolbar).subscribe(o -> finish()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_protocol;
    }

}
