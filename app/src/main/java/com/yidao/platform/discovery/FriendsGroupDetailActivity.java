package com.yidao.platform.discovery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding2.view.RxView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.discovery.bean.CommentItem;
import com.yidao.platform.discovery.bean.CommentsItem;
import com.yidao.platform.discovery.bean.DatasUtil;
import com.yidao.platform.discovery.bean.FriendsShowBean;
import com.yidao.platform.discovery.bean.User;
import com.yidao.platform.discovery.model.PyqCommentsObj;
import com.yidao.platform.discovery.presenter.FriendsGroupDetailPresenter;
import com.yidao.platform.discovery.view.CommentListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import pub.devrel.easypermissions.EasyPermissions;

public class FriendsGroupDetailActivity extends BaseActivity implements View.OnClickListener, IViewFriendsGroupDetail, EasyPermissions.PermissionCallbacks, BGANinePhotoLayout.Delegate {

    private static final int PERM_PREVIEW_PHOTO = 1;
    @BindView(R.id.iv_discovery_icon)
    ImageView ivDiscoveryIcon;
    @BindView(R.id.tv_discovery_name)
    TextView tvDiscoveryName;
    @BindView(R.id.tv_discovery_content)
    TextView tvDiscoveryContent;
    @BindView(R.id.tv_discovery_time)
    TextView tvDiscoveryTime;
    @BindView(R.id.tv_discovery_vote)
    TextView tvDiscoveryVote;
    @BindView(R.id.npl_item_moment_photos)
    BGANinePhotoLayout nplItemMomentPhotos;
    @BindView(R.id.commentList)
    CommentListView commentList;
    @BindView(R.id.tv_publish_comment)
    TextView mTvComment;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.iv_comment)
    ImageView mIvIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTitle;
    /**
     * 评论数据集合
     */
    private List<CommentsItem> mDataList;
    private BottomSheetDialog mCommentBottomSheetDialog;
    private EditText mEtContent;
    private BGANinePhotoLayout mCurrentClickNpl;
    /**
     * 是否回复别人
     */
    private User toReplyUser;
    private FriendsGroupDetailPresenter mPresenter;
    private FriendsShowBean friendsShowBean;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FriendsGroupDetailPresenter(this);
        initView();
        initData();
    }

    private void initView() {
        initToolbar();
        Intent intent = getIntent();
        userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        friendsShowBean = intent.getParcelableExtra("friendsShowBean");
        Glide.with(this).load(friendsShowBean.getHeadImg()).apply(new RequestOptions().placeholder(R.drawable.info_head_p)).into(ivDiscoveryIcon);
        tvDiscoveryName.setText(friendsShowBean.getDeployName());
        tvDiscoveryTime.setText(friendsShowBean.getDeployTime());
        tvDiscoveryVote.setText(String.valueOf(friendsShowBean.getLikeAmount()));
        tvDiscoveryVote.setCompoundDrawablesWithIntrinsicBounds(friendsShowBean.isLike() ? R.drawable.dianzan_small_done : R.drawable.dianzan_small, 0, 0, 0);
        tvDiscoveryContent.setText(friendsShowBean.getContent());
        nplItemMomentPhotos.setData(friendsShowBean.getImgUrls());
        nplItemMomentPhotos.setDelegate(this);
    }

    private void initToolbar() {
        addDisposable(RxToolbar.navigationClicks(mToolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
        mTitle.setText(R.string.discovery_pyq_title);
    }

    private void initData() {
        PyqCommentsObj obj = new PyqCommentsObj(friendsShowBean.getFindId());
        mPresenter.qryFindComms(obj);
        addDisposable(RxView.clicks(mTvComment).subscribe(o -> showCommentDialog()));
    }

    private void showAlertDialog(int messageId, DialogInterface.OnClickListener positiveListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(FriendsGroupDetailActivity.this)
                .setMessage(messageId)
                .setPositiveButton(R.string.ensure, positiveListener)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .create();
        alertDialog.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.discovery_activity_friends_group_detail;
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
        mCommentBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //when dialog cancel state write content into textview.
                mTvComment.setText(mEtContent.getText().toString());
            }
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
            case R.id.btn_comment_send: //评论内容send按钮
                // TODO: 2018/7/3 0003  当满足发送规则时，进行访问请求if success 清空et else 发送失败 doSomething
                CommentItem item = new CommentItem();
                item.setContent(mEtContent.getText().toString());
                item.setId("100");
                item.setUser(DatasUtil.getUser());
                if (toReplyUser != null) {
                    item.setToReplyUser(toReplyUser);
                }
                //mDataList.add(item);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        requestPreviewPhotoPermission();
    }

    private void requestPreviewPhotoPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            photoPreviewWrapper();
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", PERM_PREVIEW_PHOTO, perms);
        }
    }

    private void photoPreviewWrapper() {
        if (mCurrentClickNpl == null) {
            return;
        }
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "com.yidao.platform");
        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(this)
                .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
        if (mCurrentClickNpl.getItemCount() == 1) {
            // 预览单张图片
            photoPreviewIntentBuilder.previewPhoto(mCurrentClickNpl.getCurrentClickItem());
        } else if (mCurrentClickNpl.getItemCount() > 1) {
            // 预览多张图片
            photoPreviewIntentBuilder.previewPhotos(mCurrentClickNpl.getData())
                    .currentPosition(mCurrentClickNpl.getCurrentClickItemPosition()); // 当前预览图片的索引
        }
        startActivity(photoPreviewIntentBuilder.build());
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case PERM_PREVIEW_PHOTO:
                photoPreviewWrapper();
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case PERM_PREVIEW_PHOTO:
                break;
        }
    }

    @Override
    public void update2DeleteComment(CommentsItem commentsItem) {
        mDataList.remove(commentsItem);
        commentList.notifyDataSetChanged();
    }

    @Override
    public void showComments(ArrayList<CommentsItem> dataList) {
        mDataList = dataList;
        commentList.setDatas(dataList);
        if (dataList.size() > 0) {
            commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    CommentsItem commentsItem = dataList.get(position);
                    if (commentsItem.getDeployId() == 0) {  //单人的评论
                        if (String.valueOf(commentsItem.getOwnerId()).equals(userId)) {
                            showAlertDialog(R.string.ensure_delete_reply, (dialog, which) -> mPresenter.deleteComment(commentsItem));
                        } else {
                            showCommentDialog();
                        }
                    } else if (String.valueOf(commentsItem.getDeployId()).equals(userId)) {  //A回复B情况下 A是deployId
                        showAlertDialog(R.string.ensure_delete_reply, (dialog, which) -> mPresenter.deleteComment(commentsItem));
                    } else {
                        showCommentDialog();
                    }
                }
            });
        } else {
            commentList.setVisibility(View.GONE);
        }
    }
}
