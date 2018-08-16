package com.yidao.platform.read.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.BitmapUtil;
import com.yidao.platform.read.adapter.MultipleReadDetailAdapter;
import com.yidao.platform.read.adapter.ReadNewsDetailBean;
import com.yidao.platform.read.bus.WebViewLoadEvent;
import com.yidao.platform.read.presenter.ReadContentActivityPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bingoogolapple.badgeview.BGABadgeImageButton;

public class ReadContentActivity extends BaseActivity implements View.OnClickListener, IViewReadContentActivity {

    private static final int THUMB_SIZE = 150;
    @BindView(R.id.rv_read_content)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_comment)
    TextView mTvComment;
    @BindView(R.id.iv_back_comment_dialog)
    ImageView ivBack;
    @BindView(R.id.comment_dialog)
    ConstraintLayout mCommentBar;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private EditText mEtContent;
    private BGABadgeImageButton ib_comment;
    private BGABadgeImageButton ib_vote;
    private BGABadgeImageButton ib_favorite;
    private BGABadgeImageButton ib_share;
    private BottomSheetDialog mCommentBottomSheetDialog;
    private MultipleReadDetailAdapter mAdapter;
    private String url;
    private IWXAPI mWxapi;
    private BottomSheetDialog mShareBottomSheetDialog;
    private LinearLayoutManager layoutManager;
    //是否处于正在滑动状态
    private boolean isScrolling = false;
    private ReadContentActivityPresenter mPresenter;
    private long artId;
    private String userId;
    // TODO: 2018/8/16 0016  获取文章时刷新这个flag
    /**
     * 这个文章是否被用户收藏flag 这个flag状态会在获取文章详情时被刷新一次
     */
    private boolean isCollection = false;
    /**
     * 是否点收藏有操作
     */
    private boolean isOperateCollection = false;
    /**
     * 这个文章是否被用户点赞flag 这个flag状态会在获取文章详情时被刷新一次
     */
    private boolean isLike = false;
    /**
     * 是否对点赞有操作
     */
    private boolean isOperateLike = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regToWX();
        mPresenter = new ReadContentActivityPresenter(this);
        initData();
        initView();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //告知服务器阅读记录
        mPresenter.sendReadRecordInfo(artId, userId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_activity_news_content;
    }

    private void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra(Constant.STRING_URL);
        artId = intent.getLongExtra(Constant.STRING_ART_ID, 0L);
        userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
    }

    private void initView() {
        ib_comment = findViewById(R.id.ib_comment);
        ib_vote = findViewById(R.id.ib_vote);
        ib_favorite = findViewById(R.id.ib_favorite);
        ib_share = findViewById(R.id.ib_share);
        //在载入文章前展示progressbar
        mRecyclerView.setVisibility(View.INVISIBLE);
        mCommentBar.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        //-------------------------
        ib_comment.setOnClickListener(this);
        ib_vote.setOnClickListener(this);
        ib_favorite.setOnClickListener(this);
        ib_share.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        List<ReadNewsDetailBean> list = new ArrayList<>();
        list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_WEBVIEW));
        list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_HOT_COMMENT));
        for (int i = 0; i < 2; i++) {
            list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_COMMENTS));
        }
        list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_BOTTOM));
        mAdapter = new MultipleReadDetailAdapter(this, list);
        mAdapter.setWebViewUrl(url);
        mRecyclerView.setAdapter(mAdapter);
        //展开评论框
        addDisposable(RxView.clicks(mTvComment).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> showCommentDialog()));
        addDisposable(RxView.clicks(ivBack).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            int itemViewType = adapter.getItemViewType(position);
            // TODO: 2018/7/25 0025 还需要判断是否为本人评论 ignored
            if (itemViewType == ReadNewsDetailBean.ITEM_COMMENTS) {
                showAlertDialog(R.string.ensure_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteMineComment(0L);
                    }
                });
            }
            return false;
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        isScrolling = false;
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        isScrolling = true;
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        isScrolling = true;
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void showAlertDialog(int messageId, DialogInterface.OnClickListener positiveListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(ReadContentActivity.this)
                .setMessage(messageId)
                .setPositiveButton(R.string.ensure, positiveListener)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_comment_dialog:
                finish();
                break;
            case R.id.ib_comment: //评论icon
                if (!isScrolling) {
                    layoutManager.scrollToPositionWithOffset(2, 0);
                }
                break;
            case R.id.ib_vote: //收藏
                isOperateCollection = true;
                ib_vote.setSelected(!isCollection);
                isCollection = !isCollection;
                break;
            case R.id.ib_favorite: //点赞icon
                isOperateLike = true;
                ib_favorite.setSelected(!isLike);
                isLike = !isLike;
                break;
            case R.id.ib_share: //分享icon
                showShareDialog();
                break;
            case R.id.btn_comment_send: //评论内容send按钮
                String context = mEtContent.getText().toString().trim();
                if (!TextUtils.isEmpty(context)) {
                    mPresenter.pushComment(artId, context, userId);
                }
                mEtContent.setText("");
                mCommentBottomSheetDialog.cancel();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isOperateCollection) {
            mPresenter.sendCollectionInfo(isCollection, artId, userId);
        }
        if (isOperateLike) {
            mPresenter.sendLikeInfo(isLike, artId, userId);
        }
        if (mCommentBottomSheetDialog != null) {
            mCommentBottomSheetDialog.cancel();
        }
        if (mShareBottomSheetDialog != null) {
            mShareBottomSheetDialog.cancel();
        }
        EventBus.getDefault().unregister(this);
    }

    private void weChatShare(int mTargetScene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
        msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.info_head_p);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WebViewLoadEvent event) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mCommentBar.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void deleteCommentSuccess() {
        // TODO: 2018/8/16 0016 删除成功后需要刷新评论list数据 删除评论id的数据(推荐)或者重新获取
    }

    @Override
    public void deleteCommentFail() {
    }

    @Override
    public void pushCommentSuccess() {
        // TODO: 2018/8/16 0016 发布成功后需要刷新评论list数据 添加评论id的数据(推荐)或者重新获取
    }

    @Override
    public void pushCommentFail() {
    }
}
