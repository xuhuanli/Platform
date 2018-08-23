package com.yidao.platform.read.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.read.adapter.ErrorAdapter;
import com.yidao.platform.read.adapter.TitleSearchAdapter;
import com.yidao.platform.read.bean.ReadNewsBean;
import com.yidao.platform.read.presenter.SearchArticleActivityPresenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class SearchArticleActivity extends BaseActivity implements IViewSearchArticleActivity, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.recycler_result)
    RecyclerView mRecyclerView;
    private SearchArticleActivityPresenter mPresenter;
    private TitleSearchAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mPresenter = new SearchArticleActivityPresenter(this);
        addDisposable(RxView.clicks(tvCancel).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
        //initRecyclerView();
        initRecyclerView();
        initSearchView();
    }

    private void initSearchView() {
        searchView.setIconifiedByDefault(false);
        //searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchArticle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void searchArticle(String text) {
        searchView.setQuery(text, false);
        searchView.clearFocus();
        mPresenter.searchArticle(text);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_activity_search_article;
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

    @Override
    public void loadRecyclerData(ArrayList<ReadNewsBean> dataList) {
        mAdapter = new TitleSearchAdapter(dataList);
        //mAdapter.setOnLoadMoreListener(() -> loadMore(), mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        if (dataList.size() == 0) {
            View view = LayoutInflater.from(this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
            ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.no_data);
            mAdapter.setEmptyView(view);
            mAdapter.setNewData(null);
        }
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void noData() {
        ErrorAdapter adapter = new ErrorAdapter(null);
        adapter.bindToRecyclerView(mRecyclerView);
        View view = LayoutInflater.from(this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
        ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.no_data);
        adapter.setEmptyView(view);
        adapter.setNewData(null);
    }

    /**
     * 上拉加载
     */
    private void loadMore() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ReadNewsBean item = (ReadNewsBean) adapter.getItem(position);
        String url = item.getArticleContent();
        Intent intent = new Intent(this, ReadContentActivity.class);
        intent.putExtra("url", item.getArticleContent());
        intent.putExtra(Constant.STRING_ART_ID, item.getId());
        startActivity(intent);
    }
}
