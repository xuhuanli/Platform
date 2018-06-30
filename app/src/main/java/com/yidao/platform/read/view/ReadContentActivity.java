package com.yidao.platform.read.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.read.adapter.ReadContentAdapter;

import butterknife.BindView;

public class ReadContentActivity extends BaseActivity {

    @BindView(R.id.rv_read_content)
    RecyclerView mRVContent;
    private ReadContentAdapter mReadContentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRVContent.setLayoutManager(layoutManager);
        mReadContentAdapter = new ReadContentAdapter();
        mRVContent.setAdapter(mReadContentAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_activity_news_content;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReadContentAdapter.removeWebView();
    }
}
