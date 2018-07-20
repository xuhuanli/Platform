package com.yidao.platform.webview;

import android.webkit.WebChromeClient;

public class XHLWebChromeClient extends WebChromeClient {

    private XHLWebView webView;

    public XHLWebChromeClient(XHLWebView webView) {
        this.webView = webView;
    }
}
