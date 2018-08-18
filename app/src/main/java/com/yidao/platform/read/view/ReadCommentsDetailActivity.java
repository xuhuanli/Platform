package com.yidao.platform.read.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.discovery.bean.CommentItem;
import com.yidao.platform.discovery.bean.DatasUtil;
import com.yidao.platform.discovery.bean.User;
import com.yidao.platform.discovery.view.CommentDialog;
import com.yidao.platform.discovery.view.CommentListView;
import com.yidao.platform.read.presenter.ReadCommentsDetailPresenter;

import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

//这一期不做
@Deprecated
public class ReadCommentsDetailActivity extends BaseActivity implements ReadCommentsDetailInterface, View.OnClickListener {

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
    @BindView(R.id.commentList)
    CommentListView commentList;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    private ReadCommentsDetailPresenter mPresenter;
    private List<CommentItem> mDataList;
    /**
     * 是否回复别人
     */
    private User toReplyUser;
    private BottomSheetDialog mCommentBottomSheetDialog;
    private EditText mEtContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ReadCommentsDetailPresenter(this);
        initView();
        initData();
    }

    private void initView() {
        tvDiscoveryVote.setVisibility(View.GONE);
        nplItemMomentPhotos.setVisibility(View.GONE);
        ivDiscoveryIcon.setImageResource(R.drawable.info_head_p);
        tvDiscoveryName.setText("xhl");
    }

    private void initData(){
        mDataList = DatasUtil.createCommentItemList();
        commentList.setDatas(mDataList);
        addDisposable(RxView.clicks(tvComment).subscribe(o -> showCommentDialog(null)));
        if (mDataList.size() > 0) {
            commentList.setOnItemClickListener(position -> {
                CommentItem commentItem = mDataList.get(position);
                if (DatasUtil.curUser.getId().equals(commentItem.getUser().getId())) {//复制或者删除自己的评论
                    // TODO: 2018/7/17 0017 长按
                    CommentDialog dialog = new CommentDialog(ReadCommentsDetailActivity.this, mPresenter, commentItem);
                    dialog.show();
                } else {  //回复别人
                    //回复当前条目的人,所以是getUser 不是getToReplyUser
                    User toReplyUser = commentItem.getUser();
                    showCommentDialog(toReplyUser);
                }
            });
            commentList.setOnItemLongClickListener(position -> {
                CommentItem commentItem = mDataList.get(position);
                //长按进行复制或者删除
                CommentDialog dialog = new CommentDialog(ReadCommentsDetailActivity.this, mPresenter, commentItem);
                dialog.show();
            });
        } else {
            commentList.setVisibility(View.GONE);
        }
    }

    private void showCommentDialog(@Nullable User toReplyUser) {
        this.toReplyUser = toReplyUser;
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
            tvComment.setText(mEtContent.getText().toString());
        });
    }

    //内容回显
    private void fillEditText() {
        // 为 EditText 获取焦点
        mEtContent.setFocusable(true);
        mEtContent.setFocusableInTouchMode(true);
        mEtContent.requestFocus();
        mEtContent.setText(tvComment.getText());
        mEtContent.setSelection(tvComment.getText().length());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_activity_comments_detail;
    }

    @Override
    public void update2DeleteComment(CommentItem commentId) {
        mDataList.remove(commentId);
        commentList.notifyDataSetChanged();
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
                mDataList.add(item);
                commentList.notifyDataSetChanged();
                //评论完以后把ReplyUser重新置空
                toReplyUser = null;
                mEtContent.setText("");
                mCommentBottomSheetDialog.cancel();
                break;
        }
    }
}
