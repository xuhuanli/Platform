package com.yidao.platform.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class WalletActivity extends BaseActivity {
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.tv_wallet_sum)
    TextView tvWalletSum;
    @BindView(R.id.tv_wallet_withdraw)
    TextView tvWalletWithdraw;
    @BindView(R.id.cl_bord)
    ConstraintLayout clBord;
    @BindView(R.id.wallet_divider_line)
    View walletDividerLine;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        initToolbar();
        initRecyclerView();
        addDisposable(RxView.clicks(tvWalletWithdraw).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(o ->startActivity(WithdrawActivity.class)));
    }

    private void initToolbar() {
        tbTitle.setText(R.string.my_wallet);
        toolbar.setNavigationOnClickListener(o -> finish());
    }

    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        List<String> data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        WalletAdapter adapter = new WalletAdapter(R.layout.wallet_item,data);
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.wallet_activity;
    }
}
