package com.yidao.platform.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.allen.library.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.container.ContainerActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginBindingPhoneActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editText)
    EditText mEtPhone;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.button)
    Button mBtnSendMsm;
    @BindView(R.id.button2)
    Button button2;

    //最大倒计时长
    private static final long MAX_COUNT_TIME = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDisposable(RxView.clicks(button2).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                IPreference.prefHolder.getPreference(LoginBindingPhoneActivity.this).put("userId","21211");
                startActivity(ContainerActivity.class);
            }
        }));
        Observable<Long> mObservableCountTime = RxView.clicks(mBtnSendMsm)
                //防止重复点击
                .throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                //判断手机号否为空
                .flatMap(new Function<Object, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Object o) throws Exception {
                        String phone = mEtPhone.getText().toString();
                        if (TextUtils.isEmpty(phone)) {
                            ToastUtils.showToast("phone can not be empty");
                            return Observable.empty();
                        }
                        return Observable.just(true);
                    }
                })
                //将点击事件转换成倒计时事件
                .flatMap(new Function<Object, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Object o) throws Exception {
                        //更新发送按钮的状态并初始化显现倒计时文字
                        mBtnSendMsm.setEnabled(false);
                        RxTextView.text(mBtnSendMsm).accept("剩余 " + MAX_COUNT_TIME + " 秒");

                        //在实际操作中可以在此发送获取网络的请求,,续1s

                        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                                .take(MAX_COUNT_TIME)
                                //将递增数字替换成递减的倒计时数字
                                .map(new Function<Long, Long>() {
                                    @Override
                                    public Long apply(Long aLong) throws Exception {
                                        return MAX_COUNT_TIME - (aLong + 1);
                                    }
                                });
                    }
                })
                //切换到 Android 的主线程。
                .observeOn(AndroidSchedulers.mainThread());

        Consumer<Long> mConsumerCountTime = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                //当倒计时为 0 时，还原 btn 按钮
                if (aLong == 0) {
                    mBtnSendMsm.setEnabled(true);
                    RxTextView.text(mBtnSendMsm).accept("发送验证码");
                } else {
                    RxTextView.text(mBtnSendMsm).accept("剩余 " + aLong + " 秒");
                }
            }
        };
        addDisposable(mObservableCountTime.subscribe(mConsumerCountTime));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity_binding_phone;
    }
}
