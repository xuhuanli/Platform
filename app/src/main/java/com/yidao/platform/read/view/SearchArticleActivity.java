package com.yidao.platform.read.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.read.adapter.SearchWordAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class SearchArticleActivity extends BaseActivity {

    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.rv_hot_search)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        addDisposable(RxView.clicks(tvCancel).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            data.add("蛤蛤");
        }
        SearchWordAdapter adapter = new SearchWordAdapter(data);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> searchArticle(((TextView)view).getText()));
    }

    private void searchArticle(CharSequence text) {
        searchView.setQuery(text, false);
        searchView.clearFocus();
        ToastUtils.showToast("正在搜索中...");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_activity_search_article;
    }
}
