package com.yidao.platform.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.read.bus.WebViewLoadEvent;

import org.greenrobot.eventbus.EventBus;

public class XHLWebViewClient extends WebViewClient {

    public XHLWebViewClient(XHLWebView webView) {
        XHLWebView webView1 = webView;
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
        EventBus.getDefault().post(new WebViewLoadEvent());
        if (!view.getSettings().getLoadsImagesAutomatically()) {
            view.getSettings().setLoadsImagesAutomatically(true);
        }
        imgReset(view);//重置webview中img标签的图片大小
        addImageClickListener(view);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        EventBus.getDefault().post(new WebViewLoadEvent());
//        view.loadUrl("file:///android_asset/net_error.html");
    }

    private void imgReset(WebView view) {
        view.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{" +
                "objs[i].style.maxWidth = '100%'; objs[i].style.height = 'auto';  " +
                "}" +
                "})()");
    }

    private void addImageClickListener(WebView view) {
        view.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "objs[i].onclick=function()" +
                "    {  "
                + "window.android.startPhotoActivity(this.src)  " +
                "    }  " +
                "}" +
                "})()");
    }

    public static class MJavascriptInterface {
        private Context context;

        MJavascriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void startPhotoActivity(String img) {
            MyLogger.d("clicked IMG");
            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.setClass(context, PhotoBrowserActivity.class);
            context.startActivity(intent);
        }
    }
}