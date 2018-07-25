package com.yidao.platform.read.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.webview.XHLWebChromeClient;
import com.yidao.platform.webview.XHLWebView;
import com.yidao.platform.webview.XHLWebViewClient;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MultipleReadDetailAdapter extends BaseMultiItemQuickAdapter<ReadNewsDetailBean, BaseViewHolder> {

    private String url;
    private Context mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleReadDetailAdapter(Context context, List<ReadNewsDetailBean> data) {
        super(data);
        mContext = context;
        addItemType(ReadNewsDetailBean.ITEM_WEBVIEW, R.layout.read_detail_webview_item);
        addItemType(ReadNewsDetailBean.ITEM_HOT_COMMENT, R.layout.read_detail_collection_item);
        addItemType(ReadNewsDetailBean.ITEM_COMMENTS, R.layout.read_detail_comment_item);
        addItemType(ReadNewsDetailBean.ITEM_BOTTOM, R.layout.read_detail_bottom_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadNewsDetailBean item) {
        switch (item.getItemType()) {
            case ReadNewsDetailBean.ITEM_WEBVIEW:
                XHLWebView webView = helper.getView(R.id.xhlwebview_detail);
                webView.setWebViewClient(new XHLWebViewClient(webView));
                webView.setWebChromeClient(new XHLWebChromeClient(webView));
                if (url != null) {
                    webView.loadUrl(url);
                }
                break;
            case ReadNewsDetailBean.ITEM_HOT_COMMENT:
                break;
            case ReadNewsDetailBean.ITEM_COMMENTS:
                Glide.with(mContext).load(ContextCompat.getDrawable(mContext, R.drawable.mypic)).into((CircleImageView) helper.getView(R.id.iv_detail_icon));
                helper.setText(R.id.tv_detail_name, "xhl");
                helper.setText(R.id.tv_detail_comment, "好，支持，威武，有希望了");
                helper.setText(R.id.tv_detail_vote, "100");
                helper.setText(R.id.tv_detail_time, "10分钟前");
                break;
        }
    }

    public void setWebViewUrl(String url) {
        this.url = url;
    }
}
