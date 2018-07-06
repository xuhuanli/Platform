package com.yidao.platform.webview;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.daimajia.numberprogressbar.NumberProgressBar;

public class XHLWebChromeClient extends WebChromeClient {

    private XHLWebView webView;
    private NumberProgressBar progressBar;

    public XHLWebChromeClient(XHLWebView webView, NumberProgressBar progressBar) {
        this.webView = webView;
        this.progressBar = progressBar;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == 100) {
            //加载完毕进度条消失
            progressBar.setVisibility(View.GONE);
        } else {
            //更新进度
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
        }
        super.onProgressChanged(view, newProgress);
    }
}
