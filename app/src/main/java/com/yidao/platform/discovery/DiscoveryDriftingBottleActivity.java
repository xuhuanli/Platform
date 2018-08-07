package com.yidao.platform.discovery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.testpackage.bean.ApiService;
import com.yidao.platform.testpackage.bean.TestBean;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DiscoveryDriftingBottleActivity extends BaseActivity {

    /**
     * 打开发布页请求code
     */
    private static final int PUSH_BOTTLE_REQUEST = 100;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_discovery_push_bottle)
    ImageView mIvPushBottle;
    @BindView(R.id.iv_discovery_pull_bottle)
    ImageView mIvPullBottle;
    @BindView(R.id.iv_discovery_my_bottle)
    ImageView mIvMyBottle;
    @BindView(R.id.cl_rootview)
    ConstraintLayout mRootView;
    @BindView(R.id.iv_gif)
    ImageView mGif;
    @BindView(R.id.view_hide_line)
    View mHideLine;
    @BindView(R.id.cl_container)
    ConstraintLayout mClContainer;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        initToolbar();
        initStatusbar();
    }

    private void initStatusbar() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        addDisposable(RxToolbar.navigationClicks(mToolbar).subscribe(o -> finish()));
        //Glide加载Gif来做漂流瓶动画 快速搞定
        addDisposable(RxView.clicks(mIvPushBottle).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                loadPullAnim();
//                Intent intent = new Intent(DiscoveryDriftingBottleActivity.this, BottlePushActivity.class);
//                startActivityForResult(intent, PUSH_BOTTLE_REQUEST);
            }
        }));
        addDisposable(RxView.clicks(mIvPullBottle).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                mRootView.setVisibility(View.GONE);
                mToolbar.setVisibility(View.GONE);
                @SuppressLint("InflateParams") View view = LayoutInflater.from(DiscoveryDriftingBottleActivity.this).inflate(R.layout.discovery_pull_bottle_popupwindow, null);
                showPopupWindow(view);
            }
        }));
        addDisposable(RxView.clicks(mIvMyBottle).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                // TODO: 2018/7/18 0018 我的瓶子详情
                startActivity(DiscoveryMyBottleActivity.class);
            }
        }));
    }

    /**
     * 加载捡瓶子anim
     */
    private void loadPullAnim() {
        /*View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mPopupWindow = new PopupWindow(this);
        // TODO: 2018/8/6 0006 动画
        View view = LayoutInflater.from(this).inflate(R.layout.pull_bottle_anim, mClContainer, false);
        mPopupWindow.setContentView(view);
        mPopupWindow.setFocusable(true);
        //需要设置这个，否则popup的遮罩无法撑满整个屏幕（边上会有漏光）
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));// 设置背景图片，不能在布局中设置，要通过代码来设置
        mPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.showAtLocation(mClContainer, Gravity.CENTER, 0, 0);
    }

    private void showPopupWindow(View view) {
        mPopupWindow = new PopupWindow();
        mPopupWindow.setContentView(view);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#88000000")));
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.showAtLocation(mHideLine, Gravity.TOP | Gravity.LEFT, 0, 0);
        Button backSea = view.findViewById(R.id.btn_backsea);
        addDisposable(RxView.clicks(backSea).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                //再次加载动画
                mRootView.setVisibility(View.VISIBLE);
                mToolbar.setVisibility(View.VISIBLE);
                mPopupWindow.dismiss();
            }
        }));
        //回应
        Button btnReply = view.findViewById(R.id.btn_reply);
        addDisposable(RxView.clicks(btnReply).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                startActivity(DiscoveryBottleDetailActivity.class);
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.discovery_drifting_bottle_activity;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PUSH_BOTTLE_REQUEST && resultCode == BottlePushActivity.PUSH_SUCCESS) {
            //漂流瓶发布请求中，添加动画效果
            String bottleData = data.getStringExtra("data");
            String bottleCode = data.getStringExtra("code");
            MyLogger.d(bottleData + "  " + bottleCode);
            RxHttpUtils.createApi(ApiService.class)
                    .getGod()
                    .compose(Transformer.<TestBean>switchSchedulers())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) {
                            Glide.with(DiscoveryDriftingBottleActivity.this).load(R.drawable.heimao).into(mGif);
                        }
                    })
                    .subscribe(new CommonObserver<TestBean>() {
                        @Override
                        protected void onError(String errorMsg) {
                            mGif.setImageDrawable(null);
                        }

                        @Override
                        protected void onSuccess(TestBean testBean) {
                            mGif.setImageDrawable(null);
                        }
                    });
        }
    }
}
