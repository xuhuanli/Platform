package com.yidao.platform.info.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.discovery.view.FriendsGroupDetailActivity;
import com.yidao.platform.discovery.adapter.MomentAdapter;
import com.yidao.platform.discovery.bean.FriendsShowBean;
import com.yidao.platform.discovery.model.FindDiscoveryObj;
import com.yidao.platform.discovery.model.PyqFindIdObj;
import com.yidao.platform.info.presenter.MyPublishActivityPresenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class InfoMyPublishActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener, IViewMyPublishActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_my_collection)
    RecyclerView mRecyclerView;
    private String userId;
    private MyPublishActivityPresenter mPresenter;
    private FindDiscoveryObj findDiscoveryObj;
    /**
     * 请求的下一个页码
     */
    private int mNextRequestPage = 1;
    private MomentAdapter mAdapter;
    private ArrayList<FriendsShowBean> mDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mPresenter = new MyPublishActivityPresenter(this);
        initData();
    }

    private void initData() {
        userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        createObj();
        mPresenter.getFriendsList(findDiscoveryObj);
    }

    private void createObj() {
        if (findDiscoveryObj == null) {
            findDiscoveryObj = new FindDiscoveryObj();
        }
        findDiscoveryObj.setMemberId(Long.parseLong(userId));
        findDiscoveryObj.setIsContent(true);
        findDiscoveryObj.setIsImg(true);
        findDiscoveryObj.setUserId(userId);
        FindDiscoveryObj.PageBean pageBean = new FindDiscoveryObj.PageBean();
        pageBean.setPageIndex(mNextRequestPage);
        pageBean.setPageSize(Constant.PAGE_SIZE);
        findDiscoveryObj.setPage(pageBean);
    }

    @SuppressLint("CheckResult")
    private void initView() {
        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initToolbar() {
        tvTitle.setText("我的发布");
        addDisposable(RxToolbar.navigationClicks(toolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_my_publish;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_delete:
                showAlertDialog(R.string.ensure_delete_reply, (dialog, which) -> {
                    FriendsShowBean item = (FriendsShowBean) adapter.getItem(position);
                    PyqFindIdObj obj = new PyqFindIdObj(item.getFindId());
                    mPresenter.deleteFind(obj,item);
                });
                break;
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
    public void loadRecyclerData(ArrayList<FriendsShowBean> dataList) {
        mDataList = dataList;
        mAdapter = new MomentAdapter(dataList, null);
        mAdapter.setDeleteVisible(true);
        mAdapter.setOnLoadMoreListener(() -> loadMore(), mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(this, FriendsGroupDetailActivity.class);
            intent.putExtra(Constant.STRING_FIND_ID, ((FriendsShowBean) adapter.getItem(position)).getFindId());
            startActivity(intent);
        });
        mAdapter.setOnItemChildClickListener(this);
    }

    private void loadMore() {
        mNextRequestPage++;
        findDiscoveryObj.getPage().setPageIndex(mNextRequestPage);
        mPresenter.getFriendsList(findDiscoveryObj);
    }

    @Override
    public void loadMoreData(ArrayList<FriendsShowBean> dataList) {
        if (mAdapter != null) {
            mAdapter.addData(dataList);
        }
    }

    @Override
    public void deleteSuccess(FriendsShowBean item) {
        //删除成功
        mDataList.remove(item);
        mAdapter.notifyDataSetChanged();
    }
}
