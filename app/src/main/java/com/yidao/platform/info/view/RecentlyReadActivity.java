package com.yidao.platform.info.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.info.adapter.RecentReadAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class RecentlyReadActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        initToolBar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            data.add(""+i);
        }
        RecentReadAdapter adapter = new RecentReadAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }

    private void initToolBar() {
        tvTitle.setText(R.string.recent_read);
        addDisposable(RxToolbar.navigationClicks(toolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_recently_read;
    }
}
