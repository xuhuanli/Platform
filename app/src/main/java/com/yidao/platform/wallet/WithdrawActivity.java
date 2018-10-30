package com.yidao.platform.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.widget.EditText;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.DecimalDigitsInputFilter;

import butterknife.BindView;

public class WithdrawActivity extends BaseActivity {
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.tv_withdraw_label_1)
    TextView tvWithdrawLabel1;
    @BindView(R.id.tv_withdraw_label_2)
    TextView tvWithdrawLabel2;
    @BindView(R.id.tv_withdraw_label_3)
    TextView tvWithdrawLabel3;
    @BindView(R.id.tv_withdraw_all)
    TextView tvWithdrawAll;
    @BindView(R.id.tv_withdraw_icon)
    TextView tvWithdrawIcon;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;
    @BindView(R.id.tv_tips_1)
    TextView tvTips1;
    @BindView(R.id.tv_tips_2)
    TextView tvTips2;
    @BindView(R.id.tv_tips_3)
    TextView tvTips3;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        initToolbar();
//        editText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, 0)});
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(o -> finish());
        tbTitle.setText(R.string.withdraw);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.wallet_activity_withdraw;
    }
}
