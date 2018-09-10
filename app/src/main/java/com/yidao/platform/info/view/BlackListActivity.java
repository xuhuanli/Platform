package com.yidao.platform.info.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.info.adapter.BlackListAdapter;
import com.yidao.platform.info.model.BlackListBean;
import com.yidao.platform.read.adapter.ErrorAdapter;
import com.yidao.platform.read.presenter.BlackListActivityPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class BlackListActivity extends BaseActivity implements IViewBlackListActivity {
    @BindView(R.id.rv_black)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private BlackListActivityPresenter mPresenter;
    //上拉加载page控制value
    private int mNextRequestPage = 1;
    private String userId;
    private BlackListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new BlackListActivityPresenter(this);
        initView();
        initData();
    }

    @SuppressLint("CheckResult")
    private void initView() {
        userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        RxToolbar.navigationClicks(toolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        mPresenter.qryShieldUsers(mNextRequestPage, Constant.PAGE_SIZE, userId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_black_list;
    }

    @Override
    public void loadRecyclerData(List<BlackListBean.ResultBean.ListBean> dataList) {
        if (dataList.size() == 0) {
            noData(R.string.no_shiled_user);
        } else {
            mAdapter = new BlackListAdapter(dataList);
            mAdapter.setOnLoadMoreListener(this::loadMore, mRecyclerView);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                showAlertDialog(R.string.recover_user, (dialog, which) -> {
                    BlackListBean.ResultBean.ListBean item = (BlackListBean.ResultBean.ListBean) adapter.getItem(position);
                    mPresenter.cancelShieldUser(item.getUserId(), userId, item);
                });
            });
        }
    }

    private void showAlertDialog(int messageId, DialogInterface.OnClickListener positiveListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(messageId)
                .setPositiveButton(R.string.ensure, positiveListener)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();
    }


    private void loadMore() {
        mNextRequestPage++;
        mPresenter.qryShieldUsers(mNextRequestPage, Constant.PAGE_SIZE, userId);
    }

    @Override
    public void noBlackUser() {

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
    public void loadMoreData(List<BlackListBean.ResultBean.ListBean> dataList) {
        if (mAdapter != null) {
            mAdapter.addData(dataList);
        }
    }

    @Override
    public void cancelSuccess(BlackListBean.ResultBean.ListBean item) {
        List<BlackListBean.ResultBean.ListBean> data = mAdapter.getData();
        data.remove(item);
        if (data.size() == 0) {
            noData(R.string.no_shiled_user);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void noData(int resId) {
        ErrorAdapter adapter = new ErrorAdapter(null);
        adapter.bindToRecyclerView(mRecyclerView);
        View view = LayoutInflater.from(this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
        ((TextView) view.findViewById(R.id.tv_tips)).setText(resId);
        adapter.setEmptyView(view);
        adapter.setNewData(null);
    }
}
