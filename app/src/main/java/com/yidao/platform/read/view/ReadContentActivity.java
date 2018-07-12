package com.yidao.platform.read.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.read.adapter.MultipleReadDetailAdapter;
import com.yidao.platform.read.adapter.ReadNewsDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.badgeview.BGABadgeImageButton;
import io.reactivex.functions.Consumer;

public class ReadContentActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rv_read_content)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_comment)
    TextView mTvComment;
    private EditText mEtContent;
    private BGABadgeImageButton ib_comment;
    private BGABadgeImageButton ib_vote;
    private BGABadgeImageButton ib_favorite;
    private BGABadgeImageButton ib__share;
    private BottomSheetDialog mBottomSheetDialog;
    private MultipleReadDetailAdapter mAdapter;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        ib__share = findViewById(R.id.ib__share);
        ib_comment.setOnClickListener(this);
        ib_vote.setOnClickListener(this);
        ib_favorite.setOnClickListener(this);
        ib__share.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
//        ReadContentAdapter mReadContentAdapter = new ReadContentAdapter();
//        mRecyclerView.setAdapter(mReadContentAdapter);
        List<ReadNewsDetailBean> list = new ArrayList<>();
        list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_WEBVIEW));
        list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_COLLECTION));
        for (int i = 0; i < 10; i++) {
            list.add(new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_COMMENTS));
        }
        mAdapter = new MultipleReadDetailAdapter(this,list);
        mAdapter.setWebViewUrl(url);
        mRecyclerView.setAdapter(mAdapter);
        addDisposable(RxView.clicks(mTvComment).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                showBottomSheetDialog();
            }
        }));
    }

    private void showBottomSheetDialog() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_comment_fragment_dialog, null);
        mEtContent = view.findViewById(R.id.et_comment_content);
        Button mBtnCancel = view.findViewById(R.id.btn_comment_cancel);
        Button mBtnSend = view.findViewById(R.id.btn_comment_send);
        mBottomSheetDialog.setContentView(view);
        fillEditText();
        mBottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mBottomSheetDialog.show();
        mBtnCancel.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);
        //when you invoke cancel() , callback to here .So  please use dialog.cancel() but not dialog.dismiss(), unless you setOnDismissListener
        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
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
    protected int getLayoutId() {
        return R.layout.read_activity_news_content;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_comment: //评论icon
                ib_comment.showTextBadge("100");
                break;
            case R.id.ib_vote: //点赞icon
                ib_vote.showCirclePointBadge();
                break;
            case R.id.ib_favorite: //喜欢icon
                break;
            case R.id.ib__share: //分享icon
                break;
            case R.id.btn_comment_cancel: //评论内容cancel按钮
                mTvComment.setText(mEtContent.getText().toString());
                mBottomSheetDialog.cancel();
                break;
            case R.id.btn_comment_send: //评论内容send按钮
                // TODO: 2018/7/3 0003  当满足发送规则时，进行访问请求if success 清空et else 发送失败 doSomething
                mEtContent.setText("");
                mBottomSheetDialog.cancel();
                break;
        }
    }
}
