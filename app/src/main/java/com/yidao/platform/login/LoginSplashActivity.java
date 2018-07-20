package com.yidao.platform.login;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class LoginSplashActivity extends BaseActivity {

    @BindView(R.id.btn_login_by_wechat)
    Button mBtnLogin;
    private IWXAPI mWxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regToWX();
        loadAnim();
    }

    private void regToWX() {
        mWxapi = WXAPIFactory.createWXAPI(this, Constant.WX_LOGIN_APP_ID, Constant.IS_DEBUG);
        mWxapi.registerApp(Constant.WX_LOGIN_APP_ID);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity_splash;
    }

    private void loadAnim() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.login_fade_in_and_out);
        animator.setTarget(mBtnLogin);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mBtnLogin.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mBtnLogin.setClickable(true);
                addDisposable(RxView.clicks(mBtnLogin)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) {
                                wxLogin();
                                //startActivity(LoginClassificationActivity.class);
                            }
                        }));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
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
}
