package com.yidao.platform.login;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;

import butterknife.BindView;

public class LoginInterestingActivity extends BaseActivity {

    @BindView(R.id.rv_login_interest)
    RecyclerView mRVInterest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        mRVInterest.setLayoutManager(layoutManager);
        LoginInterestItemAdapter adapter = new LoginInterestItemAdapter();
        mRVInterest.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_interesting;
    }
}
