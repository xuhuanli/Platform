package com.yidao.platform.info.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding2.view.RxView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.ThreadPoolManager;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.FileUtil;
import com.yidao.platform.events.SignUpEvent;
import com.yidao.platform.info.presenter.SettingsPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class SettingsActivity extends BaseActivity implements SettingsViewInterface {

    @BindView(R.id.toolbar_info)
    Toolbar mToolbar;
    @BindView(R.id.rl_settings_cache_item)
    RelativeLayout mRlcache;
    @BindView(R.id.tv_cache_item)
    TextView mTvCache;
    @BindView(R.id.pb_cache)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_settings_about_us_item)
    TextView tvAboutUs;
    @BindView(R.id.sign_up)
    TextView tvSignUp;
    @BindView(R.id.tv_settings_black_list)
    TextView tvBlackList;
    private SettingsPresenter mPresenter;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mPresenter = new SettingsPresenter(this);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n", "CheckResult"})
    private void initView() {
        initCacheTextView();
        initToolbar();
        addDisposable(RxView.clicks(mRlcache).subscribe(o -> clearAppCache()));
        addDisposable(RxView.clicks(tvAboutUs).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> SettingsActivity.this.startActivity(AboutUsActivity.class)));
        addDisposable(RxView.clicks(tvSignUp).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            //退出登录:清除Sp下userId 跳转到登录页面
            IPreference.prefHolder.getPreference(SettingsActivity.this).remove(Constant.STRING_USER_ID);
            EventBus.getDefault().post(new SignUpEvent());
            finish();
        }));
        //黑名单
        addDisposable(RxView.clicks(tvBlackList).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(SettingsActivity.this, BlackListActivity.class);
            startActivity(intent);
        }));
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void initCacheTextView() {
        ThreadPoolManager.getInstance().addTask(() -> {
            final double cacheSize = FileUtil.getAppCacheSize(getCacheDir()) + FileUtil.getAppCacheSize(getExternalCacheDir());
            mHandler.post(() -> mTvCache.setText(String.format("%.2f", cacheSize) + "M"));
        });
    }

    private void clearAppCache() {
        ThreadPoolManager.getInstance().addTask(new ClearCacheRunnable(getCacheDir(), getExternalCacheDir()) {
            @Override
            void onClearCacheStarted() {
                mHandler.post(() -> {
                    mTvCache.setVisibility(View.GONE);
                    mPresenter.showProgressBar(mProgressBar);
                });
            }

            @Override
            void onClearCacheFinished() {
                initCacheTextView();
                mHandler.post(() -> {
                    mTvCache.setVisibility(View.VISIBLE);
                    mPresenter.dismissProgressBar(mProgressBar);
                });
            }
        });
    }

    private void initToolbar() {
        addDisposable(RxToolbar.navigationClicks(mToolbar).subscribe(o -> finish()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_settings;
    }

    @Override
    public void showProgressBar(ProgressBar progressBar) {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressbar(ProgressBar progressBar) {
        mProgressBar.setVisibility(View.GONE);
    }
}
