package com.yidao.platform.login;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.MyApplicationLike;
import com.yidao.platform.app.MyLogger;
import com.yidao.platform.app.ThreadPoolManager;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.container.ContainerActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.btn_login_by_wechat)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadAnim();
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
                            public void accept(Object o) throws Exception {
                                    startActivity(LoginClassificationActivity.class);
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
}
