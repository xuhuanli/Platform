package com.yidao.platform.read.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.BitmapUtil;
import com.yidao.platform.read.adapter.MultipleReadDetailAdapter;
import com.yidao.platform.read.adapter.ReadNewsDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.badgeview.BGABadgeImageButton;

public class ReadContentActivity extends BaseActivity implements View.OnClickListener {

    private static final int THUMB_SIZE = 150;
    @BindView(R.id.rv_read_content)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_comment)
    TextView mTvComment;
    private EditText mEtContent;
    private BGABadgeImageButton ib_comment;
    private BGABadgeImageButton ib_vote;
    private BGABadgeImageButton ib_favorite;
    private BGABadgeImageButton ib__share;
    private BottomSheetDialog mCommentBottomSheetDialog;
    private MultipleReadDetailAdapter mAdapter;
    private String url;
    private IWXAPI mWxapi;
    private BottomSheetDialog mShareBottomSheetDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regToWX();
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
    }

    private void initView() {
        ib_comment = findViewById(R.id.ib_comment);
        ib_vote = findViewById(R.id.ib_vote);
        ib_favorite = findViewById(R.id.ib_favorite);
        ib__share = findViewById(R.id.ib_share);
        ib_comment.setOnClickListener(this);
        ib_vote.setOnClickListener(this);
        ib_favorite.setOnClickListener(this);
        ib__share.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        List<ReadNewsDetailBean> list = new ArrayList<>();
        list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_WEBVIEW));
        for (int i = 0; i < 10; i++) {
            list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_COMMENTS));
        }
        list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_BOTTOM));
        mAdapter = new MultipleReadDetailAdapter(this, list);
        mAdapter.setWebViewUrl(url);
        mRecyclerView.setAdapter(mAdapter);
        addDisposable(RxView.clicks(mTvComment).subscribe(o -> showCommentDialog()));
        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            int itemViewType = adapter.getItemViewType(position);
            // TODO: 2018/7/25 0025 还需要判断是否为本人评论 ignored
            if (itemViewType == ReadNewsDetailBean.ITEM_COMMENTS) {
                showAlertDialog(R.string.ensure_delete, (dialog, which) -> ToastUtils.showToast("删除"));
            }
            return false;
        });
    }

    private void showAlertDialog(int messageId, DialogInterface.OnClickListener positiveListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(ReadContentActivity.this)
                .setMessage(messageId)
                .setPositiveButton(R.string.ensure, positiveListener)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .create();
        alertDialog.show();
    }

    private void showCommentDialog() {
        mCommentBottomSheetDialog = new BottomSheetDialog(this);
        mCommentBottomSheetDialog.setCanceledOnTouchOutside(false);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.layout_comment_fragment_dialog, null);
        mEtContent = view.findViewById(R.id.et_comment_content);
        Button mBtnSend = view.findViewById(R.id.btn_comment_send);
        mCommentBottomSheetDialog.setContentView(view);
        fillEditText();
        mCommentBottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mCommentBottomSheetDialog.show();
        mBtnSend.setOnClickListener(this);
        //when you invoke cancel() , callback to here .So  please use dialog.cancel() but not dialog.dismiss(), unless you setOnDismissListener
        mCommentBottomSheetDialog.setOnCancelListener(dialog -> {
            //when dialog cancel state write content into textview.
            mTvComment.setText(mEtContent.getText().toString());
        });
    }

    private void showShareDialog() {
        mShareBottomSheetDialog = new BottomSheetDialog(this);
        mShareBottomSheetDialog.setCanceledOnTouchOutside(true);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.layout_share_fragment_dialog, null);
        mShareBottomSheetDialog.setContentView(view);
        mShareBottomSheetDialog.show();
        view.findViewById(R.id.iv_share_msg).setOnClickListener(v -> {
            //分享到session界面
            weChatShare(SendMessageToWX.Req.WXSceneSession);
        });
        view.findViewById(R.id.iv_share_group).setOnClickListener(v -> {
            //分享到朋友圈
            weChatShare(SendMessageToWX.Req.WXSceneTimeline);
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            mShareBottomSheetDialog.cancel();
        });
    }

    //内容回显
    private void fillEditText() {
        // 为 EditText 获取焦点
        mEtContent.setFocusable(true);
        mEtContent.setFocusableInTouchMode(true);
        mEtContent.requestFocus();
        mEtContent.setText(mTvComment.getText());
        mEtContent.setSelection(mTvComment.getText().length());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.read_activity_news_content;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_comment_dialog:
                finish();
                break;
            case R.id.ib_comment: //评论icon
                ib_comment.showTextBadge("100");
                break;
            case R.id.ib_vote: //点赞icon
                ib_vote.showCirclePointBadge();
                break;
            case R.id.ib_favorite: //喜欢icon
                break;
            case R.id.ib_share: //分享icon
                showShareDialog();
                break;
            case R.id.btn_comment_send: //评论内容send按钮
                // TODO: 2018/7/3 0003  当满足发送规则时，进行访问请求if success 清空et else 发送失败 doSomething
                mEtContent.setText("");
                mCommentBottomSheetDialog.cancel();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCommentBottomSheetDialog != null) {
            mCommentBottomSheetDialog.cancel();
        }
        if (mShareBottomSheetDialog != null) {
            mShareBottomSheetDialog.cancel();
        }
    }

    private void weChatShare(int mTargetScene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.baidu.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
        msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mypic);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = mTargetScene;
        mWxapi.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void regToWX() {
        mWxapi = WXAPIFactory.createWXAPI(this, Constant.WX_LOGIN_APP_ID, Constant.IS_DEBUG);
        mWxapi.registerApp(Constant.WX_LOGIN_APP_ID);
    }
}
