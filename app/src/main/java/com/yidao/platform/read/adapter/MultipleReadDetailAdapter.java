package com.yidao.platform.read.adapter;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.RxHttpUtils;
import com.allen.library.download.DownloadObserver;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.yidao.platform.R;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.read.bus.WebViewLoadEvent;
import com.yidao.platform.webview.XHLWebChromeClient;
import com.yidao.platform.webview.XHLWebView;
import com.yidao.platform.webview.XHLWebViewClient;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;

public class MultipleReadDetailAdapter extends BaseMultiItemQuickAdapter<ReadNewsDetailBean, BaseViewHolder> {

    private String url;
    private XHLWebView mWebView;
    private int maxPage;

    public MultipleReadDetailAdapter(List<ReadNewsDetailBean> data) {
        super(data);
        addItemType(ReadNewsDetailBean.ITEM_WEBVIEW, R.layout.read_detail_webview_item);
        //addItemType(ReadNewsDetailBean.ITEM_WEBVIEW, R.layout.read_detail_pdfview_item);
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
                    mWebView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + "http://ydplatform.oss-cn-hangzhou.aliyuncs.com/test/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Android%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C.pdf");
                }
                /*String url = "http://ydplatform.oss-cn-hangzhou.aliyuncs.com/test/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Android%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C.pdf";
                String fileName = "alipay.pdf";
                RxHttpUtils
                        .downloadFile(url)
                        .subscribe(new DownloadObserver(fileName) {
                            @Override
                            protected void getDisposable(Disposable d) {
                                MyLogger.e("getDisposable");
                            }

                            @Override
                            protected void onError(String errorMsg) {
                                MyLogger.e(errorMsg);
                            }

                            @Override
                            protected void onSuccess(long bytesRead, long contentLength, float progress, boolean done, String filePath) {
                                MyLogger.e("onSuccess");
                                File file = new File(filePath);
                                if (done) {
                                    PDFView pdfView = helper.getView(R.id.pdf_view);
                                    pdfView.fromFile(file)
                                            .defaultPage(0)
                                            .pageFitPolicy(FitPolicy.BOTH)
                                            .enableAnnotationRendering(true)
                                            .scrollHandle(new DefaultScrollHandle(mContext))  //手柄
                                            .onLoad(nbPages -> {
                                                maxPage = nbPages;
                                                MyLogger.e("loadComplete:max page is +"+nbPages);
                                                EventBus.getDefault().post(new WebViewLoadEvent());
                                            })
                                            .onPageScroll(new OnPageScrollListener() {
                                                @Override
                                                public void onPageScrolled(int page, float positionOffset) {
                                                    if (page == maxPage) {

                                                    }
                                                }
                                            })
                                            .load();

                                }

                            }
                        });*/

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
