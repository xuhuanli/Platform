package com.yidao.platform.webview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class XHLWebView extends WebView {

    public XHLWebView(Context context) {
        super(context);
        init();
    }

    public XHLWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XHLWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setDefaultWebSettings(this);
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
    }

    public void setDefaultWebSettings(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //允许js代码
        webSettings.setJavaScriptEnabled(true);
        //允许SessionStorage/LocalStorage存储
        webSettings.setDomStorageEnabled(true);
        //允许缓存，设置缓存位置
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(webView.getContext().getExternalCacheDir().getPath());
        //移除部分系统JavaScript接口
        //自动加载图片 在WebViewClient finished后加载
        webSettings.setLoadsImagesAutomatically(false);
    }
}
