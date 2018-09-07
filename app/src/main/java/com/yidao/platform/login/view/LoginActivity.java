package com.yidao.platform.login.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.yidao.platform.events.WxSignInEvent;
import com.yidao.platform.login.bean.WxCodeBean;
import com.yidao.platform.login.model.LoginObj;
import com.yidao.platform.login.presenter.LoginPresenter;

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

public class LoginActivity extends BaseActivity implements IViewLoginActivity {

    @BindView(R.id.btn_login_by_wechat)
    ImageView mBtnLogin; //微信登录
    @BindView(R.id.et_phone)
    EditText etPhone; //手机号
    @BindView(R.id.btn_v_code)
    Button btnVCode; //发验证码按钮
    @BindView(R.id.et_v_code)
    EditText etVCode; //验证码
    @BindView(R.id.btn_operation)
    Button btnOperation;
    @BindView(R.id.tv_sign_in)
    TextView tvSignIn;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_register_protocol)
    TextView tvRegisterProtocol;
    @BindView(R.id.tv_user_protocol)
    TextView tvUserProtocol;
    private IWXAPI mWxapi;
    private static final long MAX_COUNT_TIME = 60;
    private LoginPresenter mPresenter;
    private boolean isSignIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int taskId = getTaskId();
        MyLogger.e("LoginActivity:所在的任务的id为: " +  taskId);
        EventBus.getDefault().register(this);
        mPresenter = new LoginPresenter(this);
        String userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        if (!TextUtils.isEmpty(userId)) {
            startActivity(ContainerActivity.class);
            finish();
        }
        regToWX();
        if (!IPreference.prefHolder.getPreference(this).contains(Constant.STRING_DEVICE_ID)) {
            mBtnLogin.setVisibility(View.INVISIBLE);
            btnOperation.setVisibility(View.INVISIBLE);
        }
        setListener();
    }

    private void regToWX() {
        mWxapi = WXAPIFactory.createWXAPI(this, Constant.WX_LOGIN_APP_ID, Constant.IS_DEBUG);
        mWxapi.registerApp(Constant.WX_LOGIN_APP_ID);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity_splash_new;
    }

    private void setListener() {
        //微信登录
        addDisposable(RxView.clicks(mBtnLogin)
                .throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribe(o -> wxLogin()));
        //验证码发送
        Observable<Long> mObservableCountTime = RxView.clicks(btnVCode)
                .throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<Object, ObservableSource<Boolean>>) o -> {
                    String phoneNum = etPhone.getText().toString();
                    if (TextUtils.isEmpty(phoneNum)) {
                        ToastUtils.showToast("电话号码不能为空");
                        return Observable.empty();
                    } else if (!PhoneRegUtil.isPhoneNumber(phoneNum)) {
                        ToastUtils.showToast("请输入正确的手机号");
                        return Observable.empty();
                    }
                    return Observable.just(true);
                })
                //将点击事件转换成倒计时事件
                .flatMap((Function<Object, ObservableSource<Long>>) o -> {
                    //请求验证码
                    mPresenter.requestVCode(etPhone.getText().toString());
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
        //登录或者注册
        addDisposable(RxView.clicks(btnOperation).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            String vCode = etVCode.getText().toString().trim();
            String phoneNum = etPhone.getText().toString();
            if (TextUtils.isEmpty(phoneNum)) {
                ToastUtils.showToast("电话号码不能为空");
                return;
            } else if (!PhoneRegUtil.isPhoneNumber(phoneNum)) {
                ToastUtils.showToast("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(vCode)) {
                ToastUtils.showToast("验证码不能为空");
                return;
            }
            String deviceId = IPreference.prefHolder.getPreference(LoginActivity.this).get(Constant.STRING_DEVICE_ID, IPreference.DataType.STRING);
            if (isSignIn) {
                LoginObj obj = new LoginObj(deviceId, "Android", phoneNum, vCode);
                mPresenter.phoneSignIn(obj);
            } else {
                //走注册接口
                LoginObj obj = new LoginObj(deviceId, "Android", phoneNum, vCode);
                mPresenter.registerAccount(obj);
            }
        }));
        //切换label
        addDisposable(RxView.clicks(tvRegister).subscribe(o -> {
            isSignIn = false;
            changeTextDisplay(tvSignIn, tvRegister);
            tvRegisterProtocol.setVisibility(View.VISIBLE);
            tvUserProtocol.setVisibility(View.VISIBLE);
            btnOperation.setText(R.string.register);
        }));
        addDisposable(RxView.clicks(tvSignIn).subscribe(o -> {
            isSignIn = true;
            changeTextDisplay(tvRegister, tvSignIn);
            tvRegisterProtocol.setVisibility(View.INVISIBLE);
            tvUserProtocol.setVisibility(View.INVISIBLE);
            btnOperation.setText(R.string.sign_in);
        }));
        addDisposable(RxView.clicks(tvUserProtocol).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> startActivity(new Intent(LoginActivity.this, ProtocolActivity.class))));
    }

    private void changeTextDisplay(TextView gray, TextView bold) {
        gray.setTypeface(null, Typeface.NORMAL);
        gray.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        gray.setTextColor(Color.parseColor("#ff999999"));
        bold.setTypeface(null, Typeface.BOLD);
        bold.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        bold.setTextColor(Color.parseColor("#ff333333"));
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
        btnOperation.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(WxSignInEvent event) {
        WxCodeBean.ResultBean result = event.getResult();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void sendCodeSuccess() {
        ToastUtils.showToast("验证码发送成功");
    }

    @Override
    public void bindSuccess(WxCodeBean.ResultBean result) {
        MyLogger.e(result.toString());
        IPreference.prefHolder.getPreference(this).put(Constant.STRING_USER_TOKEN, result.getToken());
        IPreference.prefHolder.getPreference(this).put(Constant.STRING_USER_REFRESHTOKEN, result.getRefreshToken());
        //已绑定，需要写入userId
        IPreference.prefHolder.getPreference(this).put(Constant.STRING_USER_ID, result.getUserId());
        startActivity(new Intent(this, ContainerActivity.class));
        finish();
    }

    @Override
    public void showInfo(String info) {
        ToastUtils.showToast(info);
    }

    @Override
    public void hasBindToWx() {
        ToastUtils.showToast("该手机号已绑定微信登录，请尝试微信登录");
    }

    @Override
    public void needRegister() {
        ToastUtils.showToast("手机号暂未注册，已为您跳到注册页");
        isSignIn = false;
        changeTextDisplay(tvSignIn, tvRegister);
        tvRegisterProtocol.setVisibility(View.VISIBLE);
        tvUserProtocol.setVisibility(View.VISIBLE);
        btnOperation.setText(R.string.register);
    }
}
