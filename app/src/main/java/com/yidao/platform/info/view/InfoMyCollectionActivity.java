package com.yidao.platform.info.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.info.adapter.CollectionAdapter;
import com.yidao.platform.info.presenter.MyCollectionActivityPresenter;
import com.yidao.platform.read.adapter.ErrorAdapter;
import com.yidao.platform.read.bean.ReadNewsBean;
import com.yidao.platform.read.view.ReadContentActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class InfoMyCollectionActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener, IViewMyCollectionActivity {

    @BindView(R.id.rv_my_collection)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private CollectionAdapter mCollectionAdapter;
    private MyCollectionActivityPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        initToolBar();
        mPresenter = new MyCollectionActivityPresenter(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initToolBar() {
        tvTitle.setText("我的收藏");
        addDisposable(RxToolbar.navigationClicks(mToolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                finish();
            }
        }));
    }

    private void initData() {
        String userId = IPreference.prefHolder.getPreference(this).get("userId", IPreference.DataType.STRING);
        mPresenter.getUserCollectArtList(userId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_my_collections;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, ReadContentActivity.class);
        intent.putExtra("url", "http://news.163.com/");
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        // TODO: 2018/7/18 0018 delete it
        return false;
    }

    @Override
    public void loadRecyclerData(ArrayList<ReadNewsBean> dataList) {
        //这里就不做null判断new Adapter了 因为没有分页所以只会回调到1次
        mCollectionAdapter = new CollectionAdapter(dataList);
        mRecyclerView.setAdapter(mCollectionAdapter);
        if (dataList.size() == 0) {
            mCollectionAdapter.bindToRecyclerView(mRecyclerView);
            View view = LayoutInflater.from(this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
            ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.no_collection);
            mCollectionAdapter.setEmptyView(view);
            mCollectionAdapter.setNewData(null);
        }
        mCollectionAdapter.setOnItemClickListener(this);
        mCollectionAdapter.setOnItemLongClickListener(this);
    }

    @Override
    public void showError() {
        ErrorAdapter adapter = new ErrorAdapter(null);
        adapter.bindToRecyclerView(mRecyclerView);
        View view = LayoutInflater.from(this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
        ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.connection_failed);
        adapter.setEmptyView(view);
        adapter.setNewData(null);
    }
}
