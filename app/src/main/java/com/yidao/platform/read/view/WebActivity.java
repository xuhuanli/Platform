package com.yidao.platform.read.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.webview.XHLWebChromeClient;
import com.yidao.platform.webview.XHLWebView;
import com.yidao.platform.webview.XHLWebViewClient;

import butterknife.BindView;

public class WebActivity extends BaseActivity {

    @BindView(R.id.webView)
    XHLWebView webView;
    @BindView(R.id.toolbar_info)
    Toolbar toolbarInfo;
    @BindView(R.id.iv_activity)
    ImageView ivActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String url = intent.getStringExtra(Constant.STRING_ACTIVITY);
        MyLogger.e(url);
        addDisposable(RxToolbar.navigationClicks(toolbarInfo).subscribe(o -> finish()));
        if (url.endsWith("png") || url.endsWith("jpg")) {
            webView.setVisibility(View.GONE);
            ivActivity.setVisibility(View.VISIBLE);
            Glide.with(this).load(url).into(ivActivity);
        } else {
            webView.setWebViewClient(new XHLWebViewClient(webView));
            webView.setWebChromeClient(new XHLWebChromeClient(webView));
            webView.setVisibility(View.VISIBLE);
            ivActivity.setVisibility(View.GONE);
            webView.loadUrl(url);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_web_activity;
    }
}
