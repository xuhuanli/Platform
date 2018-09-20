package com.yidao.platform.read.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.read.adapter.ErrorAdapter;
import com.yidao.platform.read.adapter.ReadItemMoreAdapter;
import com.yidao.platform.read.bean.ReadNewsBean;
import com.yidao.platform.read.presenter.ReadItemMoreActivityPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class ReadItemMoreActivity extends BaseActivity implements IViewReadItemMoreActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_my_collection)
    RecyclerView mRecyclerView;
    private ReadItemMoreAdapter mAdapter;
    private ReadItemMoreActivityPresenter mPresenter;
    private long categoryId;
    //上拉加载page控制value
    private int mNextRequestPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ReadItemMoreActivityPresenter(this);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        categoryId = intent.getLongExtra("categoryId", 0L);
        String categoryName = intent.getStringExtra("categoryName");
        setTitle(categoryName);
        mPresenter.getCategoryArticleExt(categoryId, String.valueOf(1), String.valueOf(Constant.PAGE_SIZE));
    }

    private void setTitle(String title) {
        tvTitle.setText(title);
    }

    @SuppressLint("CheckResult")
    private void initView() {
        RxToolbar.navigationClicks(toolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_activity_item_more;
    }

    @Override
    public void loadRecyclerData(List<ReadNewsBean> dataList) {
        mAdapter = new ReadItemMoreAdapter(dataList);
        mAdapter.setOnLoadMoreListener(this::loadMore, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ReadNewsBean item = (ReadNewsBean) adapter.getItem(position);
            Intent intent = new Intent(this, ReadContentActivity.class);
            intent.putExtra("url", item.getArticleContent());
            intent.putExtra(Constant.STRING_ART_ID, item.getId());
            startActivity(intent);
        });
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

    @Override
    public void loadMoreData(List<ReadNewsBean> dataList) {
        if (mAdapter != null) {
            mAdapter.addData(dataList);
        }
    }

    @Override
    public void noData() {
        ErrorAdapter adapter = new ErrorAdapter(null);
        adapter.bindToRecyclerView(mRecyclerView);
        View view = LayoutInflater.from(this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
        ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.no_category_art);
        adapter.setEmptyView(view);
        adapter.setNewData(null);
    }

    private void loadMore() {
        mNextRequestPage++;
        mPresenter.getCategoryArticleExt(categoryId, String.valueOf(mNextRequestPage), String.valueOf(Constant.PAGE_SIZE));
    }
}
