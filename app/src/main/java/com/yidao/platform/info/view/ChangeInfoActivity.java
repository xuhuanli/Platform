package com.yidao.platform.info.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.info.model.EventChangeInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class ChangeInfoActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.toolbar_info)
    Toolbar toolbarInfo;
    @BindView(R.id.et_value)
    EditText etValue;
    private String title;
    public static final int NICKNAME = 1;
    public static final int STATUS = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        title = intent.getStringExtra(Constant.STRING_TITLE);
        String value = intent.getStringExtra(Constant.STRING_VALUE);
        tvTitle.setText(title);
        etValue.setText(value);
        addDisposable(RxView.clicks(tvSave).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                String newValue = etValue.getText().toString().trim();
                if (TextUtils.equals(title, "昵称")) {
                    if (newValue.length()>12) {
                        ToastUtils.showToast("昵称不能超过12个字");
                        return;
                    }
                }
                if (TextUtils.equals(title, "简介")) {
                    if (newValue.length()>24) {
                        ToastUtils.showToast("简介不能超过24个字");
                        return;
                    }
                }
                if (!TextUtils.isEmpty(newValue)) {
                    EventBus.getDefault().post(new EventChangeInfo(title, newValue));
                    finish();
                }
            }
        }));
        addDisposable(RxToolbar.navigationClicks(toolbarInfo).subscribe(o -> finish()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_change_info;
    }
}
