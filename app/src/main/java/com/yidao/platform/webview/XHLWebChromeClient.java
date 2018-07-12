package com.yidao.platform.webview;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.daimajia.numberprogressbar.NumberProgressBar;

public class XHLWebChromeClient extends WebChromeClient {

    private XHLWebView webView;

    public XHLWebChromeClient(XHLWebView webView) {
        this.webView = webView;
    }
}
