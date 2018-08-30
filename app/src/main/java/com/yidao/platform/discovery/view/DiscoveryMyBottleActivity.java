package com.yidao.platform.discovery.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.yidao.platform.discovery.bean.MyBottleBean;
import com.yidao.platform.discovery.presenter.IViewMyBottleActivity;
import com.yidao.platform.discovery.presenter.MyBottleAdapter;
import com.yidao.platform.discovery.presenter.MyBottlePresenter;
import com.yidao.platform.read.adapter.ErrorAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class DiscoveryMyBottleActivity extends BaseActivity implements IViewMyBottleActivity, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private MyBottlePresenter mPresenter;
    private String userId;
    private int mNextRequestPage = 1;
    private MyBottleAdapter mAdapter;
    private List<MyBottleBean.ListBean> mDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mPresenter = new MyBottlePresenter(this);
        initData();
    }

    private void initView() {
        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @SuppressLint("CheckResult")
    private void initToolbar() {
        RxToolbar.navigationClicks(toolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish());
    }

    private void initData() {
        userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        mPresenter.qryBottleList(userId, String.valueOf(mNextRequestPage), String.valueOf(Constant.PAGE_SIZE));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.discovery_activity_my_bottle;
    }

    @Override
    public void errorNet() {
        ErrorAdapter adapter = new ErrorAdapter(null);
        adapter.bindToRecyclerView(mRecyclerView);
        View view = LayoutInflater.from(this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
        ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.connection_failed);
        adapter.setEmptyView(view);
        adapter.setNewData(null);
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
    public void loadRecyclerData(List<MyBottleBean.ListBean> dataList) {
        mDataList = dataList;
        mAdapter = new MyBottleAdapter(mDataList);
        mAdapter.setOnLoadMoreListener(() -> loadMore(), mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        if (dataList.size() == 0) {
            View view = LayoutInflater.from(this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
            ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.no_bottle_message);
            mAdapter.setEmptyView(view);
            mAdapter.setNewData(null);
        }
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
    }

    private void loadMore() {
        mNextRequestPage++;
        mPresenter.qryBottleList(userId, String.valueOf(mNextRequestPage), String.valueOf(Constant.PAGE_SIZE));
    }

    @Override
    public void loadMoreData(List<MyBottleBean.ListBean> dataList) {
        if (mAdapter != null) {
            mAdapter.addData(dataList);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MyBottleBean.ListBean item = (MyBottleBean.ListBean) adapter.getItem(position);
        Intent intent = new Intent(this, DiscoveryBottleDetailActivity.class);
        intent.putExtra(Constant.STRING_BOTTLE_ID, item.getBottleId() + "");
        intent.putExtra(Constant.STRING_SESSION_ID, item.getSessionId() + "");
        intent.putExtra(Constant.STRING_BOTTLE_PAGE_FROM, "2");
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        showAlertDialog(R.string.ensure_delete_bottle, (dialog, which) -> {
            MyBottleBean.ListBean item = (MyBottleBean.ListBean) adapter.getItem(position);
            mPresenter.deleteBottle(String.valueOf(item.getBottleId()), userId);
            mDataList.remove(item);
            mAdapter.notifyDataSetChanged();
        });
        return false;
    }

    private void showAlertDialog(int messageId, DialogInterface.OnClickListener positiveListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(messageId)
                .setPositiveButton(R.string.ensure, positiveListener)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();
    }
}
