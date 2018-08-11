package com.yidao.platform.webview;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class XHLWebChromeClient extends WebChromeClient {

    private XHLWebView webView;

    public XHLWebChromeClient(XHLWebView webView) {
        this.webView = webView;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }
}
