package com.yidao.platform.read.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yidao.platform.R;
import com.yidao.platform.webview.XHLWebChromeClient;
import com.yidao.platform.webview.XHLWebView;
import com.yidao.platform.webview.XHLWebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public enum ITEM_TYPE {
        ITEM_TYPE_WEBVIEW,
        ITEM_TYPE_COLLECTION,
        ITEM_TYPE_COMMENT
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_WEBVIEW.ordinal()) { //加载头部webview
            return new WebViewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.read_detail_webview_item, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_COLLECTION.ordinal()) { //加载收藏分类
            return new CollectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.read_detail_hot_comment_item, parent, false));
        } else { //加载评论列表
            return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.read_detail_comment_item, parent, false));
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
        @BindView(R.id.fl)
        FrameLayout fl_container;
        @BindView(R.id.iv_error)
        ImageView errorImg;
        @BindView(R.id.xhlwebview_detail)
        XHLWebView webView;

        WebViewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //initWebView(itemView);
            initWebView();
        }

        private void initWebView() {
            webView.setWebViewClient(new XHLWebViewClient(webView));
            webView.setWebChromeClient(new XHLWebChromeClient(webView));
            webView.loadUrl("http://www.edaochina.com/");
        }
    }

    class CollectionViewHolder extends RecyclerView.ViewHolder {

        CollectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

