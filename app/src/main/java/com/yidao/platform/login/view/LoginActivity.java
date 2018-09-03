package com.yidao.platform.login.view;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.allen.library.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.DeviceIdEvent;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.app.utils.PhoneRegUtil;
import com.yidao.platform.container.ContainerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_login_by_wechat)
    ImageView mBtnLogin;
    /*@BindView(R.id.btn_v_code)
    Button btnVCode;*/
    private IWXAPI mWxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        String userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        if (!TextUtils.isEmpty(userId)) {
            startActivity(ContainerActivity.class);
            finish();
        }
        regToWX();
        if (!IPreference.prefHolder.getPreference(this).contains(Constant.STRING_DEVICE_ID)) {
            mBtnLogin.setVisibility(View.INVISIBLE);
        }
        setListener();
    }

    private void regToWX() {
        mWxapi = WXAPIFactory.createWXAPI(this, Constant.WX_LOGIN_APP_ID, Constant.IS_DEBUG);
        mWxapi.registerApp(Constant.WX_LOGIN_APP_ID);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity_splash;
    }

    private void setListener() {
        addDisposable(RxView.clicks(mBtnLogin)
                .throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribe(o -> wxLogin()));
        /*Observable<Long> mObservableCountTime = RxView.clicks(btnVCode)
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
                    MyLogger.e("参数：" + userId);
                    mPresenter.requestVCode(etPhone.getText().toString(), userId);
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
        addDisposable(mObservableCountTime.subscribe(mConsumerCountTime));*/
    }

    private void wxLogin() {
        if (!mWxapi.isWXAppInstalled()) {
            Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        mWxapi.sendReq(req);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DeviceIdEvent event) {
        mBtnLogin.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
