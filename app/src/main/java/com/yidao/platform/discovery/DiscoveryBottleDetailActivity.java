package com.yidao.platform.discovery;

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
import com.yidao.platform.discovery.bean.CommentItem;
import com.yidao.platform.discovery.bean.DatasUtil;
import com.yidao.platform.discovery.bean.PickBottleBean;
import com.yidao.platform.discovery.bean.User;
import com.yidao.platform.discovery.model.PyqCommentsObj;
import com.yidao.platform.discovery.model.ReplyBottleObj;
import com.yidao.platform.discovery.presenter.DiscoveryBottleDetailPresenter;
import com.yidao.platform.discovery.view.CommentDialog;
import com.yidao.platform.discovery.view.CommentListView;
import com.yidao.platform.discovery.view.DiscoveryBottleDetailInterface;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

public class DiscoveryBottleDetailActivity extends BaseActivity implements DiscoveryBottleDetailInterface, View.OnClickListener {
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
    CommentListView commentList;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.tv_publish_comment)
    TextView tvPublishComment;
    @BindView(R.id.tv_title)
    TextView mTitle;
    /**
     * 评论数据集合
     */
    private List<CommentItem> mDataList;
    private BottomSheetDialog mCommentBottomSheetDialog;
    private EditText mEtContent;
    /**
     * 是否回复别人
     */
    private User toReplyUser;
    private DiscoveryBottleDetailPresenter mPresenter;
    private PickBottleBean.ResultBean result;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DiscoveryBottleDetailPresenter(this);
        Intent intent = getIntent();
        userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        result = intent.getParcelableExtra(Constant.STRING_BOTTLE);
        initView();
        initData();
    }

    private void initView() {
        //瓶子不展示点赞和九宫格
        addDisposable(RxToolbar.navigationClicks(toolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
        tvDiscoveryVote.setVisibility(View.GONE);
        nplItemMomentPhotos.setVisibility(View.GONE);
        commentList.setVisibility(View.GONE);
        mTitle.setText("瓶子详情");
        Glide.with(this).load(result.getImgUrl()).into(ivDiscoveryIcon);
        tvDiscoveryContent.setText(result.getContent());
        tvDiscoveryName.setText(result.getNickName());
        tvDiscoveryTime.setText(result.getCreateTime());
    }

    private void initData() {
        mDataList = DatasUtil.createCommentItemList();
        //commentList.setDatas(mDataList);
        addDisposable(RxView.clicks(tvPublishComment).subscribe(o -> showCommentDialog(1,null)));
        if (mDataList.size() > 0) {
            commentList.setOnItemClickListener(position -> {
                CommentItem commentItem = mDataList.get(position);
                if (DatasUtil.curUser.getId().equals(commentItem.getUser().getId())) {//复制或者删除自己的评论
                    // TODO: 2018/7/17 0017 长按
                    CommentDialog dialog = new CommentDialog(DiscoveryBottleDetailActivity.this, mPresenter, commentItem);
                    dialog.show();
                } else {  //回复别人
                    //回复当前条目的人,所以是getUser 不是getToReplyUser
                    User toReplyUser = commentItem.getUser();
                    //showCommentDialog(toReplyUser);
                }
            });
            commentList.setOnItemLongClickListener(position -> {
                CommentItem commentItem = mDataList.get(position);
                //长按进行复制或者删除
                CommentDialog dialog = new CommentDialog(DiscoveryBottleDetailActivity.this, mPresenter, commentItem);
                dialog.show();
            });
        } else {
            commentList.setVisibility(View.GONE);
        }
    }

    private void showCommentDialog(long ownerId, String deployId) {
        mCommentBottomSheetDialog = new BottomSheetDialog(this);
        mCommentBottomSheetDialog.setCanceledOnTouchOutside(true);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.layout_comment_fragment_dialog, null);
        mEtContent = view.findViewById(R.id.et_comment_content);
        Button mBtnSend = view.findViewById(R.id.btn_comment_send);
        mCommentBottomSheetDialog.setContentView(view);
        fillEditText();
        mCommentBottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mCommentBottomSheetDialog.show();
        mBtnSend.setOnClickListener(v -> {
            String content = mEtContent.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                ReplyBottleObj obj = new ReplyBottleObj();
                obj.setBottleId(String.valueOf(result.getId()));
                obj.setContent(content);
                obj.setDeviceId(IPreference.prefHolder.getPreference(DiscoveryBottleDetailActivity.this).getString(Constant.STRING_DEVICE_ID));
                obj.setParMessageId("0");
                obj.setParUserId(String.valueOf(result.getUserId()));
                obj.setSessionId("0");
                obj.setSessionType("0");
                obj.setUserId(userId);
                mPresenter.replyBottle(obj);
            }
        });
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
    public void update2DeleteComment(CommentItem commentItem) {
        mDataList.remove(commentItem);
        commentList.notifyDataSetChanged();
    }

    @Override
    public void showErrorInfo(String info) {
        ToastUtils.showToast(info);
    }

    @Override
    public void success() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment_send: //评论内容send按钮
                String msg = mEtContent.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    return;
                }
                ReplyBottleObj obj = new ReplyBottleObj();
                obj.setBottleId(String.valueOf(result.getId()));
                obj.setContent(msg);
                obj.setDeviceId(IPreference.prefHolder.getPreference(this).getString(Constant.STRING_DEVICE_ID));
                obj.setParMessageId("0");
                obj.setParUserId(String.valueOf(result.getUserId()));
                obj.setSessionId("0");
                obj.setSessionType("0");
                obj.setUserId(userId);
                mPresenter.replyBottle(obj);


                // TODO: 2018/7/3 0003  当满足发送规则时，进行访问请求if success 清空et else 发送失败 doSomething
                CommentItem item = new CommentItem();
                item.setContent(mEtContent.getText().toString());
                item.setId("100");
                item.setUser(DatasUtil.getUser());
                if (toReplyUser != null) {
                    item.setToReplyUser(toReplyUser);
                }
                mDataList.add(item);
                commentList.notifyDataSetChanged();
                //评论完以后把ReplyUser重新置空
                toReplyUser = null;
                mEtContent.setText("");
                mCommentBottomSheetDialog.cancel();
                break;
        }
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
