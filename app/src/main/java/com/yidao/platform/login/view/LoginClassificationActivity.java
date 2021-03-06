package com.yidao.platform.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;

import butterknife.BindView;

public class LoginClassificationActivity extends BaseActivity {

    @BindView(R.id.tv_entrepreneur)
    TextView mTvEntrepreneur;
    @BindView(R.id.tv_investor)
    TextView mTvInvestor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        addDisposable(RxView.clicks(mTvEntrepreneur).subscribe(o -> startActivity(LoginInterestingActivity.class)));
        addDisposable(RxView.clicks(mTvInvestor).subscribe(o -> startActivity(LoginInterestingActivity.class)));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity_classification;
    }
}
