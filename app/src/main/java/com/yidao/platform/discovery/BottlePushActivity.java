package com.yidao.platform.discovery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class BottlePushActivity extends BaseActivity {

    public static final int PUSH_SUCCESS = 100;
    @BindView(R.id.tv_bottle_cancel)
    TextView tvBottleCancel;
    @BindView(R.id.tv_bottle_push)
    TextView tvBottlePush;
    @BindView(R.id.et_bottle_content)
    EditText mBottleContent;
    @BindView(R.id.tv_content_length)
    TextView mContentLength;
    @BindView(R.id.tv_want)
    TextView tvWant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        addDisposable(RxTextView.textChanges(mBottleContent).subscribe(new Consumer<CharSequence>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mContentLength.setText(String.format("%d%s", charSequence.length(), getString(R.string.bottle_base_length)));
            }
        }));
        addDisposable(RxView.clicks(tvBottleCancel).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        }));
        addDisposable(RxView.clicks(tvBottlePush).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                // TODO: 2018/7/14 0014 if push success
                Intent intent = new Intent();
                intent.putExtra("data","这是瓶子里面的内容");
                intent.putExtra("code","这是想给谁看到");
                setResult(PUSH_SUCCESS,intent);
                finish();
            }
        }));
        addDisposable(RxView.clicks(tvWant).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.discovery_activity_push_bottle;
    }
}
