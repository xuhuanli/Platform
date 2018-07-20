package com.yidao.platform.info.view;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.info.adapter.BottleViewAdapter;
import com.yidao.platform.info.adapter.CommentViewAdapter;
import com.yidao.platform.info.adapter.SystemViewAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class InfoMyMessageActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_comment)
    Button btnComment;
    @BindView(R.id.btn_bottle)
    Button btnBottle;
    @BindView(R.id.btn_system)
    Button btnSystem;
    @BindView(R.id.fl_msg_container)
    FrameLayout flMsgContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @SuppressLint("CheckResult")
    private void initView() {
        tvTitle.setText("我的消息");
        RxToolbar.navigationClicks(toolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });
        setDefaultLabel();
        btnComment.setOnClickListener(this);
        btnBottle.setOnClickListener(this);
        btnSystem.setOnClickListener(this);
    }

    private void setDefaultLabel() {
        keepSelector(btnComment);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            arrayList.add("hhhh");
        }
        CommentViewAdapter adapter = new CommentViewAdapter(arrayList);
        flMsgContainer.addView(creatCommentView(adapter));
    }

    private void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_my_message;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment:
                keepSelector(btnComment);
                ArrayList<String> commentDatas = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    commentDatas.add("commentDatas");
                }
                CommentViewAdapter commentViewAdapter = new CommentViewAdapter(commentDatas);
                flMsgContainer.removeAllViews();
                flMsgContainer.addView(creatCommentView(commentViewAdapter));
                break;
            case R.id.btn_bottle:
                keepSelector(btnBottle);
                ArrayList<String> bottleDatas = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    bottleDatas.add("bottleDatas");
                }
                BottleViewAdapter bottleViewAdapter = new BottleViewAdapter(bottleDatas);
                flMsgContainer.removeAllViews();
                flMsgContainer.addView(creatCommentView(bottleViewAdapter));
                break;
            case R.id.btn_system:
                keepSelector(btnSystem);
                ArrayList<String> systemDatas = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    systemDatas.add("systemDatas");
                }
                SystemViewAdapter systemViewAdapter = new SystemViewAdapter(systemDatas);
                flMsgContainer.removeAllViews();
                flMsgContainer.addView(creatCommentView(systemViewAdapter));
                break;
        }
    }

    private void keepSelector(Button selectorBtn) {
        this.btnComment.setTypeface(Typeface.DEFAULT);
        this.btnBottle.setTypeface(Typeface.DEFAULT);
        this.btnSystem.setTypeface(Typeface.DEFAULT);
        selectorBtn.setTypeface(Typeface.DEFAULT_BOLD);
    }

    /**
     * 评论布局
     *
     * @param adapter extends RecyclerView.Adapter
     * @return CommentView
     */
    private View creatCommentView(RecyclerView.Adapter adapter) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.info_message_label, null);
        RecyclerView rvCommentView = (RecyclerView) view.findViewById(R.id.rv_msg);
        rvCommentView.setLayoutManager(new LinearLayoutManager(this));
        rvCommentView.setAdapter(adapter);
        return view;
    }
}
