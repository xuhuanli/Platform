package com.yidao.platform.read.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.webview.XHLWebView;

import butterknife.BindView;

public class WebActivity extends BaseActivity {

    @BindView(R.id.webView)
    XHLWebView webView;
    @BindView(R.id.toolbar_info)
    Toolbar toolbarInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String url = intent.getStringExtra(Constant.STRING_ACTIVITY);
        webView.loadUrl(url);
        addDisposable(RxToolbar.navigationClicks(toolbarInfo).subscribe(o -> finish()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_web_activity;
    }
}
