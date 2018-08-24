package com.yidao.platform.read.adapter;

import android.webkit.WebView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yidao.platform.R;
import com.yidao.platform.app.utils.ConvertFormatUtil;
import com.yidao.platform.webview.XHLWebChromeClient;
import com.yidao.platform.webview.XHLWebView;
import com.yidao.platform.webview.XHLWebViewClient;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MultipleReadDetailAdapter extends BaseMultiItemQuickAdapter<ReadNewsDetailBean, BaseViewHolder> {

    private String url;
    private XHLWebView mWebView;

    public MultipleReadDetailAdapter(List<ReadNewsDetailBean> data) {
        super(data);
        addItemType(ReadNewsDetailBean.ITEM_WEBVIEW, R.layout.read_detail_webview_item);
        addItemType(ReadNewsDetailBean.ITEM_HOT_COMMENT, R.layout.read_detail_hot_comment_item);
        addItemType(ReadNewsDetailBean.ITEM_COMMENTS, R.layout.read_detail_comment_item);
        addItemType(ReadNewsDetailBean.ITEM_BOTTOM, R.layout.read_detail_bottom_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadNewsDetailBean item) {
        switch (item.getItemType()) {
            case ReadNewsDetailBean.ITEM_WEBVIEW:
                mWebView = helper.getView(R.id.xhlwebview_detail);
                mWebView.setWebViewClient(new XHLWebViewClient(mWebView));
                mWebView.setWebChromeClient(new XHLWebChromeClient(mWebView));
                if (url != null) {
                    mWebView.loadUrl(url);
                }
                break;
            case ReadNewsDetailBean.ITEM_HOT_COMMENT:
                break;
            case ReadNewsDetailBean.ITEM_COMMENTS:
                Glide.with(mContext).load(item.getHeadImg()).into((CircleImageView) helper.getView(R.id.iv_detail_icon));
                helper.setText(R.id.tv_detail_name, item.getNickName())
                        .setText(R.id.tv_detail_comment, item.getContent())
                        .setText(R.id.tv_detail_vote, item.getLikeCount())
                        .setText(R.id.tv_detail_time, ConvertFormatUtil.convertTime(item.getTimeSamp()));
                helper.addOnClickListener(R.id.tv_detail_vote);
                break;
        }
    }

    public void setWebViewUrl(String url) {
        this.url = url;
    }

    public WebView getWebView() {
        return mWebView;
    }
}
