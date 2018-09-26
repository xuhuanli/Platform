package com.yidao.platform.read.adapter;

import android.webkit.WebView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    private XHLWebView mWebView;

    public MultipleReadDetailAdapter(List<ReadNewsDetailBean> data) {
        super(data);
        addItemType(ReadNewsDetailBean.ITEM_WEBVIEW, R.layout.read_detail_webview_item);
        addItemType(ReadNewsDetailBean.ITEM_HOT_COMMENT, R.layout.read_detail_hot_comment_item);
        addItemType(ReadNewsDetailBean.ITEM_COMMENTS, R.layout.read_detail_comment_item);
        addItemType(ReadNewsDetailBean.ITEM_LAST_COMMENT, R.layout.read_detail_last_comment_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadNewsDetailBean item) {
        switch (item.getItemType()) {
            case ReadNewsDetailBean.ITEM_WEBVIEW:
                mWebView = helper.getView(R.id.xhlwebview_detail);
                mWebView.setWebChromeClient(new XHLWebChromeClient(mWebView));
                if (!url.isEmpty()) {
                    mWebView.setWebViewClient(new XHLWebViewClient(mWebView));
                    mWebView.loadUrl(url);
                }
                break;
            case ReadNewsDetailBean.ITEM_HOT_COMMENT:
                break;
            case ReadNewsDetailBean.ITEM_COMMENTS:
                Glide.with(mContext).load(item.getHeadImg()).apply(RequestOptions.placeholderOf(R.drawable.info_head_p)).into((CircleImageView) helper.getView(R.id.iv_detail_icon));
                helper.setText(R.id.tv_detail_name, item.getNickName())
                        .setText(R.id.tv_detail_comment, item.getContent())
                        .setText(R.id.tv_detail_time, item.getTimeSamp());
                TextView dianZan = helper.getView(R.id.tv_detail_vote);
                dianZan.setText(item.getLikeCount());
                dianZan.setCompoundDrawablesWithIntrinsicBounds(item.isLikedCommed() ? R.drawable.dianzan_small_done : R.drawable.dianzan_small, 0, 0, 0);
                helper.addOnClickListener(R.id.tv_detail_vote);
                helper.addOnClickListener(R.id.iv_detail_icon);
                break;
            case ReadNewsDetailBean.ITEM_LAST_COMMENT:
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
