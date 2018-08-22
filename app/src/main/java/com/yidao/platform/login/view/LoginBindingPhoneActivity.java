package com.yidao.platform.login.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.app.utils.PhoneRegUtil;
import com.yidao.platform.container.ContainerActivity;
import com.yidao.platform.login.model.BindPhoneObj;
import com.yidao.platform.login.presenter.LoginBindingPhonePresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginBindingPhoneActivity extends BaseActivity implements IViewBindingPhoneActivity{

    //最大倒计时长
    private static final long MAX_COUNT_TIME = 60;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.btn_v_code)
    Button btnVCode;
    @BindView(R.id.et_v_code)
    EditText etVCode;
    @BindView(R.id.tv_register_protocol)
    TextView tvRegisterProtocol;
    @BindView(R.id.btn_ensure)
    Button btnEnsure;
    private LoginBindingPhonePresenter mPresenter;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LoginBindingPhonePresenter(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setProtocol();
    }

    private void initView() {
        userId = getIntent().getStringExtra(Constant.STRING_USER_ID);
        addDisposable(RxView.clicks(btnEnsure).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            String vCode = etVCode.getText().toString().trim();
            BindPhoneObj obj = new BindPhoneObj(vCode, userId);
            mPresenter.bindPhone(obj);
        }));
        Observable<Long> mObservableCountTime = RxView.clicks(btnVCode)
                .throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<Object, ObservableSource<Boolean>>) o -> {
                    String phone = etPhone.getText().toString();
                    if (TextUtils.isEmpty(phone)) {
                        ToastUtils.showToast("电话号码不能为空");
                        return Observable.empty();
                    } else if (!PhoneRegUtil.checkPhoneNumber(phone)) {
                        ToastUtils.showToast("请输入正确的手机号");
                        return Observable.empty();
                    }
                    return Observable.just(true);
                })
                //将点击事件转换成倒计时事件
                .flatMap((Function<Object, ObservableSource<Long>>) o -> {
                    //请求验证码
                    MyLogger.e("参数："+userId);
                    mPresenter.requestVCode(etPhone.getText().toString(),userId);
                    //更新发送按钮的状态并初始化显现倒计时文字
                    btnVCode.setEnabled(false);
                    btnVCode.setBackgroundColor(Color.GRAY);
                    RxTextView.text(btnVCode).accept("剩余 " + MAX_COUNT_TIME + " 秒");
                    //在实际操作中可以在此发送获取网络的请求,,续1s
                    return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                            .take(MAX_COUNT_TIME)
                            //将递增数字替换成递减的倒计时数字
                            .map(aLong -> MAX_COUNT_TIME - (aLong + 1));
                })
                //切换到 Android 的主线程。
                .observeOn(AndroidSchedulers.mainThread());
        Consumer<Long> mConsumerCountTime = aLong -> {
            //当倒计时为 0 时，还原 btn 按钮
            if (aLong == 0) {
                btnVCode.setEnabled(true);
                btnVCode.setBackgroundColor(Color.parseColor("#ff007aff"));
                RxTextView.text(btnVCode).accept("获取验证码");
            } else {
                RxTextView.text(btnVCode).accept("剩余 " + aLong + " 秒");
            }
        };
        addDisposable(mObservableCountTime.subscribe(mConsumerCountTime));
    }

    private void setProtocol() {
        String str = getString(R.string.register_protocol);
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtils.showToast("跳转到协议page");
            }
        };
        builder.setSpan(clickableSpan, 10, str.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#009ad6"));
        builder.setSpan(colorSpan, 10, str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvRegisterProtocol.setText(builder);
        tvRegisterProtocol.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity_binding_phone;
    }

    @Override
    public void sendCodeSuccess() {
        ToastUtils.showToast("验证码发送成功");
    }

    @Override
    public void bindSuccess() {
        IPreference.prefHolder.getPreference(this).put(Constant.STRING_USER_ID,userId);
        startActivity(ContainerActivity.class);
    }
}