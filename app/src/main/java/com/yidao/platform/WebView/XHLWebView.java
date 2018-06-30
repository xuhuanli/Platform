package com.yidao.platform.WebView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.github.lzyzsd.jsinterface.PhotoBrowserActivity;
import com.yidao.platform.app.MyLogger;

public class XHLWebView extends WebView {


    private Context context;
    private ImageView mErrorFrame;
    private NumberProgressBar mProgressBar;

    public XHLWebView(Context context) {
        super(context);
        this.context = context;
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
        this.setWebViewClient(generateXHLWebViewClient());
        this.setWebChromeClient(generateXHLWebChromeClient());
    }

    private WebChromeClient generateXHLWebChromeClient() {
        return new XHLWebChromeClient(this);
    }

    private WebViewClient generateXHLWebViewClient() {
        return new XHLWebViewClient(this);
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
        webSettings.setAppCachePath(context.getExternalCacheDir().getPath());
        //移除部分系统JavaScript接口
        //自动加载图片 在WebViewClient finished后加载
        webSettings.setLoadsImagesAutomatically(false);
    }

    public void setErrorImg(ImageView errorImg) {
        mErrorFrame = errorImg;
    }

    public void setProgressBar(NumberProgressBar numberProgressBar) {
        mProgressBar = numberProgressBar;
    }

    private class XHLWebViewClient extends WebViewClient {
        private XHLWebView webView;

        XHLWebViewClient(XHLWebView webView) {
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
            if (!webView.getSettings().getLoadsImagesAutomatically()) {
                webView.getSettings().setLoadsImagesAutomatically(true);
            }
            // TODO: 2018/6/30 0030 待网页加载完全后设置图片点击的监听方法
            webView.addJavascriptInterface(new MJavascriptInterface(view.getContext()), "imagelistener");
            addImageClickListener(view);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mErrorFrame.setVisibility(View.VISIBLE);
        }

        private void addImageClickListener(WebView view) {
            webView.loadUrl("javascript:(function(){" +
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

    class XHLWebChromeClient extends WebChromeClient {

        private XHLWebView webView;

        XHLWebChromeClient(XHLWebView webView) {
            this.webView = webView;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                //加载完毕进度条消失
                mProgressBar.setVisibility(View.GONE);
            } else {
                //更新进度
                mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
