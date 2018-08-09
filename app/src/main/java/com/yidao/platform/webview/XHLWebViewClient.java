package com.yidao.platform.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yidao.platform.app.utils.MyLogger;

public class XHLWebViewClient extends WebViewClient {
    private XHLWebView webView;

    public XHLWebViewClient(XHLWebView webView) {
        this.webView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        final Uri uri = Uri.parse(url);
        return handleUri(view, uri);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        final Uri uri = request.getUrl();
        return handleUri(view, uri);
    }

    private boolean handleUri(WebView view, Uri uri) {
        MyLogger.d("Uri = " + uri);
        final String scheme = uri.getScheme();
        final String host = uri.getHost();
        // Based on some condition you need to determine if you are going to load the url
        // in your web view itself or in a browser.
        // You can use `host` or `scheme` or any part of the `uri` to decide.
        if (scheme.startsWith("http:") || scheme.startsWith("https:")) {
            view.loadUrl(uri.getPath());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (!view.getSettings().getLoadsImagesAutomatically()) {
            view.getSettings().setLoadsImagesAutomatically(true);
        }
        // TODO: 2018/6/30 0030 待网页加载完全后设置图片点击的监听方法
        //view.addJavascriptInterface(new MJavascriptInterface(view.getContext()), "imagelistener");
        //addImageClickListener(view);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
    }

    private void addImageClickListener(WebView view) {
        view.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistener.startPhotoActivity(this.src);  " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
                "    }  " +
                "}" +
                "})()");
    }

    class MJavascriptInterface {
        private Context context;

        MJavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void startPhotoActivity(String imageUrls) {
            Intent intent = new Intent();
            intent.putExtra("imageUrls", imageUrls);
            intent.setClass(context, PhotoBrowserActivity.class);
            context.startActivity(intent);
        }
    }
}