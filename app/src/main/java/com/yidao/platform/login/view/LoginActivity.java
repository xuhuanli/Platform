package com.yidao.platform.login.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_login_by_wechat)
    ImageView mBtnLogin;
    private IWXAPI mWxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);
        regToWX();
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
        //startActivity(new Intent(LoginActivity.this, LoginBindingPhoneActivity.class));
    }

    private void wxLogin() {
        if (!mWxapi.isWXAppInstalled()) {
            // TODO: 2018/8/16 0016 是否要引导用户去下载微信
            Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        mWxapi.sendReq(req);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }
}
