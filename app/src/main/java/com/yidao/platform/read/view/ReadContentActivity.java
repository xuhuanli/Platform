package com.yidao.platform.read.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.allen.library.utils.ToastUtils;
import com.bumptech.glide.Glide;
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
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.events.ThumbEvent;
import com.yidao.platform.read.adapter.MultipleReadDetailAdapter;
import com.yidao.platform.read.adapter.ReadNewsDetailBean;
import com.yidao.platform.read.bean.ShareBean;
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
    private BottomSheetDialog mCommentBottomSheetDialog;
    private MultipleReadDetailAdapter mAdapter;
    private IWXAPI mWxapi;
    private BottomSheetDialog mShareBottomSheetDialog;
    private LinearLayoutManager layoutManager;
    //是否处于正在滑动状态
    private boolean isScrolling = false;
    private ReadContentActivityPresenter mPresenter;
    private long artId;
    private String userId;
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
    private int mNextRequestPage = 1;
    private List<ReadNewsDetailBean> list = new ArrayList<>();
    private boolean isFirstGetNewComment = true;
    private int artLikeCount;
    private int artCommentConut;
    private ReadNewsDetailBean deleteItem;
    private int indexOfLastTitleItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regToWX();
        EventBus.getDefault().register(this);
        mPresenter = new ReadContentActivityPresenter(this);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.sendReadRecordInfo(artId, userId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_activity_news_content;
    }

    private void initData() {
        mPresenter.getHotComments(artId, userId);
    }

    private void initView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(Constant.STRING_URL);
        artId = intent.getLongExtra(Constant.STRING_ART_ID, 0L);
        userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        configCommentBar();
        //在载入文章前展示progressbar
        mRecyclerView.setVisibility(View.INVISIBLE);
        mCommentBar.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        //-------------------------
        initRecyclerView();
        //展开评论框
        addDisposable(RxView.clicks(mTvComment).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> showCommentDialog()));
        addDisposable(RxView.clicks(ivBack).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
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

        });
        list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_WEBVIEW));
        mAdapter = new MultipleReadDetailAdapter(list);
        mAdapter.setWebViewUrl(url);
        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            deleteItem = (ReadNewsDetailBean) adapter.getItem(position);
            int itemViewType = adapter.getItemViewType(position);
            if (TextUtils.equals(deleteItem.getUserId(), userId) && itemViewType == ReadNewsDetailBean.ITEM_COMMENTS) {
                showAlertDialog(R.string.ensure_delete, (dialog, which) -> mPresenter.deleteMineComment(deleteItem.getId()));
            }
            return false;
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ReadNewsDetailBean item = (ReadNewsDetailBean) adapter.getItem(position);
            boolean isLike = item.isLikedCommed();
            if (isLike) {
                mPresenter.userUnlikeComment(item.getId(), userId);
                item.setLikeCount(String.valueOf(Long.parseLong(item.getLikeCount()) - 1));
            } else {
                mPresenter.userLikeComment(item.getId(), userId);
                item.setLikeCount(String.valueOf(Long.parseLong(item.getLikeCount()) + 1));
            }
            item.setLikedCommed(!isLike);
            mAdapter.notifyItemChanged(position);
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void configCommentBar() {
        ib_comment = findViewById(R.id.ib_comment);
        ib_vote = findViewById(R.id.ib_vote);
        ib_favorite = findViewById(R.id.ib_favorite);
        BGABadgeImageButton ib_share = findViewById(R.id.ib_share);
        ib_comment.setOnClickListener(this);
        ib_vote.setOnClickListener(this);
        ib_favorite.setOnClickListener(this);
        ib_share.setOnClickListener(this);
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(null);
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
        mCommentBottomSheetDialog.setCanceledOnTouchOutside(true);
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
                isCollection = !isCollection;
                ib_vote.setSelected(isCollection);
                break;
            case R.id.ib_favorite: //点赞icon
                isOperateLike = true;
                isLike = !isLike;
                ib_favorite.setSelected(isLike);
                artLikeCount = isLike ? artLikeCount + 1 : artLikeCount - 1;
                if (artLikeCount < 0) {
                    artLikeCount = 0;
                }
                ib_favorite.showTextBadge(String.valueOf(artLikeCount));
                break;
            case R.id.ib_share: //分享icon
                mPresenter.getShareContent(String.valueOf(artId));
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

    private void regToWX() {
        mWxapi = WXAPIFactory.createWXAPI(this, Constant.WX_LOGIN_APP_ID, Constant.IS_DEBUG);
        mWxapi.registerApp(Constant.WX_LOGIN_APP_ID);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WebViewLoadEvent event) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mCommentBar.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        MyLogger.e("获取到浏览器加载完毕回调");
    }

    @Override
    public void deleteCommentSuccess() {
        list.remove(deleteItem);
        artCommentConut = artCommentConut - 1;
        ib_comment.showTextBadge(String.valueOf(artCommentConut));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteCommentFail() {
    }

    @Override
    public void pushCommentSuccess(ReadNewsDetailBean item) {
        artCommentConut = artCommentConut + 1;
        ib_comment.showTextBadge(artCommentConut == 0 ? null : String.valueOf(artCommentConut));
        if (list.size() == 1) {
            ReadNewsDetailBean titleItem = new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_LAST_COMMENT);
            list.add(titleItem);
            list.add(item);
            indexOfLastTitleItem = list.indexOf(titleItem);
        } else {
            list.add(indexOfLastTitleItem + 1, item);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void pushCommentFail() {
        ToastUtils.showToast("评论发布失败");
    }

    @Override
    public void showHotComment(boolean isCollectArt, String commentAmount, String likeAmount, ArrayList<ReadNewsDetailBean> dataList, boolean isLikedtArt) {
        isCollection = isCollectArt;
        isLike = isLikedtArt;
        artLikeCount = Integer.parseInt(likeAmount);
        artCommentConut = Integer.parseInt(commentAmount);
        ib_vote.setSelected(isCollectArt);
        ib_favorite.setSelected(isLike);
        if (!"0".equals(commentAmount)) {
            ib_comment.showTextBadge(commentAmount);
        }
        if (!"0".equals(likeAmount)) {
            ib_favorite.showTextBadge(likeAmount);
        }
        if (dataList.size() > 0) {
            list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_HOT_COMMENT));
            list.addAll(dataList);
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnLoadMoreListener(this::loadMore, mRecyclerView);
    }

    @Override
    public void loadMoreData(List<ReadNewsDetailBean> dataList) {
        if (isFirstGetNewComment) {
            if (dataList.size() > 0) {
                ReadNewsDetailBean lastTitleItem = new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_LAST_COMMENT);
                list.add(lastTitleItem);
                indexOfLastTitleItem = list.indexOf(lastTitleItem);
                list.addAll(dataList);
            } else {
                mAdapter.loadMoreEnd(true);
            }
            isFirstGetNewComment = false;
        } else {
            list.addAll(dataList);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void showShareDialog(String title, String subTitle, Bitmap bitmap, String shareUrl) {
        mShareBottomSheetDialog = new BottomSheetDialog(this);
        mShareBottomSheetDialog.setCanceledOnTouchOutside(true);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.layout_share_fragment_dialog, null);
        mShareBottomSheetDialog.setContentView(view);
        mShareBottomSheetDialog.show();
        view.findViewById(R.id.iv_share_msg).setOnClickListener(v -> {
            //分享到session界面
            weChatShare(SendMessageToWX.Req.WXSceneSession, title, subTitle, bitmap, shareUrl);
        });
        view.findViewById(R.id.iv_share_group).setOnClickListener(v -> {
            //分享到朋友圈
            weChatShare(SendMessageToWX.Req.WXSceneTimeline, title, subTitle, bitmap, shareUrl);
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(v -> mShareBottomSheetDialog.cancel());
    }

    private void weChatShare(int mTargetScene, String title, String subTitle, Bitmap bitmap, String shareUrl) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = subTitle;
        msg.thumbData = BitmapUtil.bitmapBytes(bitmap, 32);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(artId);
        req.message = msg;
        req.scene = mTargetScene;
        mWxapi.sendReq(req);
    }

    @Override
    public void setShareContent(ShareBean.ResultBean result) {
        new Thread(() -> {
            try {
                Bitmap bitmap = Glide.with(ReadContentActivity.this).asBitmap().load(result.getHomeImg()).submit(THUMB_SIZE, THUMB_SIZE).get();
                EventBus.getDefault().post(new ThumbEvent(bitmap, result.getTitle(), result.getSubtitle(), result.getLinkUrl()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getThumbEvent(ThumbEvent event) {
        showShareDialog(event.getTitle(), event.getSubTitle(), event.getBitmap(), event.getShareUrl());
    }

    @Override
    public void loadMoreEnd(boolean b) {
        if (mAdapter != null) {
            mAdapter.loadMoreEnd(b);
        }
    }

    @Override
    public void loadMoreComplete() {
        if (mAdapter != null) {
            mAdapter.loadMoreComplete();
        }
    }

    private void loadMore() {
        mPresenter.getLastestComments(artId, mNextRequestPage, Constant.PAGE_SIZE, userId);
        mNextRequestPage++;
    }
}
