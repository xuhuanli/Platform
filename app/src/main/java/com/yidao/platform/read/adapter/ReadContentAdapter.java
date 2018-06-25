package com.yidao.platform.read.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.yidao.platform.R;

public class ReadContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private WebView mWebView;

    public static enum ITEM_TYPE {
        ITEM_TYPE_WEBVIEW,
        ITEM_TYPE_COLLECTION,
        ITEM_TYPE_COMMENT
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_WEBVIEW.ordinal()) { //加载头部webview
            mWebView = new BridgeWebView(parent.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mWebView.setLayoutParams(layoutParams);
            //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.read_recycle_webview_item, parent, false);
            return new WebViewViewHolder(mWebView);
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_COLLECTION.ordinal()) { //加载收藏分类
            return new WebViewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.read_recycle_collection_item, parent, false));
        } else { //加载评论列表
            return new WebViewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.read_recycle_comment_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return ITEM_TYPE.ITEM_TYPE_WEBVIEW.ordinal();
            case 1:
                return ITEM_TYPE.ITEM_TYPE_COLLECTION.ordinal();
            default:
                return ITEM_TYPE.ITEM_TYPE_COMMENT.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class WebViewViewHolder extends RecyclerView.ViewHolder {

        WebViewViewHolder(View itemView) {
            super(itemView);
            if (itemView instanceof WebView) {
                initWebView(itemView);
            }
        }

        private void initWebView(View itemView) {
            mWebView = (WebView) itemView;
            WebSettings mSetting = mWebView.getSettings();
            mSetting.setJavaScriptEnabled(true);
            //mWebView.setWebChromeClient(new WebChromeClient());
            //mWebView.setWebViewClient(new WebViewClient() {
            //});
            mWebView.loadUrl("https://www.baidu.com");
        }
    }

    class CollectionViewHolder extends RecyclerView.ViewHolder {

        public CollectionViewHolder(View itemView) {
            super(itemView);
        }
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void removeWebView(){
        mWebView.destroy();
    }
}

