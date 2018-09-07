package com.yidao.platform.webview;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class XHLWebChromeClient extends WebChromeClient {

    public XHLWebChromeClient(XHLWebView webView) {
        XHLWebView webView1 = webView;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }
}
