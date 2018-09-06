package com.yidao.platform.discovery.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding2.view.RxView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.discovery.bean.BottleDtlBean;
import com.yidao.platform.discovery.model.ReplyBottleListObj;
import com.yidao.platform.discovery.model.ReplyBottleObj;
import com.yidao.platform.discovery.presenter.DiscoveryBottleDetailPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import io.reactivex.functions.Consumer;

public class DiscoveryBottleDetailActivity extends BaseActivity implements DiscoveryBottleDetailInterface {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_discovery_icon)
    ImageView ivDiscoveryIcon;
    @BindView(R.id.tv_discovery_name)
    TextView tvDiscoveryName;
    @BindView(R.id.tv_discovery_time)
    TextView tvDiscoveryTime;
    @BindView(R.id.tv_discovery_vote)
    TextView tvDiscoveryVote;
    @BindView(R.id.tv_discovery_content)
    TextView tvDiscoveryContent;
    @BindView(R.id.npl_item_moment_photos)
    BGANinePhotoLayout nplItemMomentPhotos;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.commentList)
    CommentListViewForBottle commentList;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.tv_publish_comment)
    TextView tvPublishComment;
    @BindView(R.id.tv_title)
    TextView mTitle;

    private BottomSheetDialog mCommentBottomSheetDialog;
    private EditText mEtContent;
    private DiscoveryBottleDetailPresenter mPresenter;
    private String userId;
    private String bottleId;
    private String sessionId;
    private BottleDtlBean.ResultBean result;
    private String flag;
    private Button mBtnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DiscoveryBottleDetailPresenter(this);
        initView();
        initData();
    }

    private void initView() {
        initToolbar();
        Intent intent = getIntent();
        bottleId = intent.getStringExtra(Constant.STRING_BOTTLE_ID);
        sessionId = intent.getStringExtra(Constant.STRING_SESSION_ID);
        flag = intent.getStringExtra(Constant.STRING_BOTTLE_PAGE_FROM);
        userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        tvDiscoveryVote.setVisibility(View.GONE);
        nplItemMomentPhotos.setVisibility(View.GONE);
    }

    private void initToolbar() {
        addDisposable(RxToolbar.navigationClicks(toolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
        mTitle.setText("瓶子详情");
    }

    private void initData() {
        mPresenter.qryBottleDtl(bottleId, sessionId);
    }

    private void showCommentDialog(String parMessageId, String parUserId, String sessionId) {
        mCommentBottomSheetDialog = new BottomSheetDialog(this);
        mCommentBottomSheetDialog.setCanceledOnTouchOutside(true);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.layout_comment_fragment_dialog, null);
        mEtContent = view.findViewById(R.id.et_comment_content);
        mBtnSend = view.findViewById(R.id.btn_comment_send);
        mCommentBottomSheetDialog.setContentView(view);
        fillEditText();
        mCommentBottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mCommentBottomSheetDialog.show();
        addDisposable(RxView.clicks(mBtnSend).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(o -> {
            String content = mEtContent.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                ReplyBottleObj obj = new ReplyBottleObj();
                obj.setBottleId(result.getBottleId());
                obj.setContent(content);
                obj.setDeviceId(IPreference.prefHolder.getPreference(DiscoveryBottleDetailActivity.this).getString(Constant.STRING_DEVICE_ID));
                obj.setParMessageId(parMessageId);
                obj.setParUserId(parUserId);
                obj.setSessionId(sessionId);
                obj.setSessionType("0");
                obj.setUserId(userId);
                mPresenter.replyBottle(obj);
            }
        }));
        //when you invoke cancel() , callback to here .So  please use dialog.cancel() but not dialog.dismiss(), unless you setOnDismissListener
        mCommentBottomSheetDialog.setOnCancelListener(dialog -> {
            //when dialog cancel state write content into textview.
            tvPublishComment.setText(mEtContent.getText().toString());
        });
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
        addDisposable(RxView.clicks(mBtnSend).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(o -> {
            String content = mEtContent.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                ReplyBottleListObj obj = new ReplyBottleListObj();
                obj.setContent(content);
                obj.setDeviceId(IPreference.prefHolder.getPreference(DiscoveryBottleDetailActivity.this).getString(Constant.STRING_DEVICE_ID));
                obj.setSessionId(sessionId);
                obj.setSessionType("0");
                obj.setUserId(userId);
                obj.setBottleId(bottleId);
                mPresenter.replyBottleList(obj);
            }
        }));
        //when you invoke cancel() , callback to here .So  please use dialog.cancel() but not dialog.dismiss(), unless you setOnDismissListener
        mCommentBottomSheetDialog.setOnCancelListener(dialog -> {
            //when dialog cancel state write content into textview.
            tvPublishComment.setText(mEtContent.getText().toString());
        });
    }

    //内容回显
    private void fillEditText() {
        // 为 EditText 获取焦点
        mEtContent.setFocusable(true);
        mEtContent.setFocusableInTouchMode(true);
        mEtContent.requestFocus();
        mEtContent.setText(tvPublishComment.getText());
        mEtContent.setSelection(tvPublishComment.getText().length());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.discovery_activity_bottle_detail;
    }

    @Override
    public void showErrorInfo(String info) {
        ToastUtils.showToast(info);
    }

    @Override
    public void commentSuccess() {
        mEtContent.setText("");
        mCommentBottomSheetDialog.cancel();
        if (TextUtils.equals(flag, "1")) {
            ToastUtils.showToast("回复成功,请到我的瓶子查看对话");
            finish();
        } else {
            mPresenter.qryBottleDtl(bottleId, sessionId);
        }
    }

    @Override
    public void replyLimited() {
        mEtContent.setText("");
        mCommentBottomSheetDialog.cancel();
        finish();
    }

    @Override
    public void showBottleDtl(BottleDtlBean.ResultBean result) {
        this.result = result;
        Glide.with(this).load(result.getHeadImg()).into(ivDiscoveryIcon);
        tvDiscoveryContent.setText(result.getContent());
        tvDiscoveryName.setText(result.getUserName());
        tvDiscoveryTime.setText(result.getTimeStamp());
        List<BottleDtlBean.ResultBean.MessBean> mess = result.getMess();
        if (mess.size() == 0) {
            commentList.setVisibility(View.GONE);
        } else {
            commentList.setDatas(mess);
        }
        addDisposable(RxView.clicks(tvPublishComment).subscribe(o -> {
            if (TextUtils.equals(flag, "1")) {//来自捞瓶子
                showCommentDialog("0", result.getAuthorId(), "0");
            } else {
                showCommentDialog();
            }
        }));
        addDisposable(RxView.clicks(ivComment).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            if (TextUtils.equals(flag, "1")) {//来自捞瓶子
                showCommentDialog("0", result.getAuthorId(), "0");
            } else {
                showCommentDialog();
            }
        }));
    }

    @Override
    protected void onDestroy() {
        if (mCommentBottomSheetDialog != null) {
            mCommentBottomSheetDialog.cancel();
        }
        if (commentList != null) {
            commentList = null;
        }
        super.onDestroy();
    }
}
