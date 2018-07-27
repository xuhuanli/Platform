package com.yidao.platform.info.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
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

public class InfoMyMessageActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tb_info_my_msg)
    TabLayout mTabLayout;
    @BindView(R.id.rv_msg)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_none_msg)
    ImageView mNoneMsgPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        initTabLayout();
        tvTitle.setText("我的消息");
        addDisposable(RxToolbar.navigationClicks(toolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
    }

    private void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText("系统通知"));
        mTabLayout.addTab(mTabLayout.newTab().setText("评论消息"));
        mTabLayout.addTab(mTabLayout.newTab().setText("我漂流瓶"));
        setDefaultTabView();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ArrayList<String> systemDatas = new ArrayList<>();
                        for (int i = 0; i < 4; i++) {
                            systemDatas.add("systemDatas");
                        }
                        if (systemDatas.size() == 0){
                            noMsg();
                        }else {
                            SystemViewAdapter systemViewAdapter = new SystemViewAdapter(systemDatas);
                            setTabViewData(systemViewAdapter);
                        }
                        break;
                    case 1:
                        ArrayList<String> commentDatas = new ArrayList<>();
                        for (int i = 0; i < 4; i++) {
                            commentDatas.add("commentDatas");
                        }
                        CommentViewAdapter commentViewAdapter = new CommentViewAdapter(commentDatas);
                        setTabViewData(commentViewAdapter);
                        break;
                    case 2:
                        ArrayList<String> bottleDatas = new ArrayList<>();
                        for (int i = 0; i < 4; i++) {
                            bottleDatas.add("bottleDatas");
                        }
                        BottleViewAdapter bottleViewAdapter = new BottleViewAdapter(bottleDatas);
                        setTabViewData(bottleViewAdapter);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void noMsg() {
        mRecyclerView.setVisibility(View.GONE);
        mNoneMsgPic.setVisibility(View.VISIBLE);
        mNoneMsgPic.setBackgroundColor(Color.parseColor("#ffd8d8d8"));
    }

    private void hasMsg() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoneMsgPic.setVisibility(View.GONE);
    }

    private void setTabViewData(RecyclerView.Adapter adapter) {
        hasMsg();
        configRecyclerView(adapter);
    }

    private void setDefaultTabView() {
        ArrayList<String> systemDatas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            systemDatas.add("systemDatas");
        }
        SystemViewAdapter systemViewAdapter = new SystemViewAdapter(systemDatas);
        setTabViewData(systemViewAdapter);
    }

    private void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_my_message;
    }

    private void configRecyclerView(RecyclerView.Adapter adapter) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
