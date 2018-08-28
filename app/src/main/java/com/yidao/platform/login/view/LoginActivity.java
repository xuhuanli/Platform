package com.yidao.platform.login.view;

import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.DeviceIdEvent;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.container.ContainerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_login_by_wechat)
    ImageView mBtnLogin;
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
