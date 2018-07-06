package com.yidao.platform.info.view;

import android.annotation.SuppressLint;
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
import com.yidao.platform.R;
import com.yidao.platform.app.ThreadPoolManager;
import com.yidao.platform.app.utils.FileUtil;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.info.presenter.SettingsPresenter;

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
    private SettingsPresenter mPresenter;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mPresenter = new SettingsPresenter(this);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void initView() {
        initCacheTextView();
        initToolbar();
        RxView.clicks(mRlcache).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                clearAppCache();
            }
        });
    }

    private void initCacheTextView() {
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                final double cacheSize = FileUtil.getAppCacheSize(getCacheDir()) + FileUtil.getAppCacheSize(getExternalCacheDir());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvCache.setText(String.format("%.2f", cacheSize) + "M");
                    }
                });
            }
        });
    }

    private void clearAppCache() {
        ThreadPoolManager.getInstance().addTask(new ClearCacheRunnable(getCacheDir(), getExternalCacheDir()) {
            @Override
            void onClearCacheStarted() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvCache.setVisibility(View.GONE);
                        mPresenter.showProgressBar(mProgressBar);
                    }
                });
            }

            @Override
            void onClearCacheFinished() {
                initCacheTextView();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTvCache.setVisibility(View.VISIBLE);
                        mPresenter.dismissProgressBar(mProgressBar);
                    }
                }, 1000);
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        addDisposable(RxToolbar.navigationClicks(mToolbar).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        }));
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
