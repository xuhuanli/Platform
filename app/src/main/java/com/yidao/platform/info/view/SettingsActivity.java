package com.yidao.platform.info.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.yidao.platform.login.view.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

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
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Map<String, String> result = (Map<String, String>) msg.obj;
                    Toast.makeText(SettingsActivity.this, "支付成功 查看log", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

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
            startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
            finish();
        }));
        //黑名单
        addDisposable(RxView.clicks(tvBlackList).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
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

    //test alipay
    public void alipay(View view) {
        //String orderStr = String.format("app_id=2018101261694052&method=alipay.trade.app.pay&charset=utf-8&sign_type=RSA2&timestamp=%s&version=1.0&notify_url=%s&biz_content={\"timeout_express\":\"30m\",\"seller_id\":\"\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"0.01\",\"subject\":\"1\",\"out_trade_no\":\"ZQLM3O56MJD4SK3\"}&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D",timestamp,notify_url);

        String orderStr = "alipay_sdk=alipay-sdk-java-3.3.87.ALL&app_id=2018101261694052&biz_content=%7B%22body%22%3A%22tradeId%3Dnull%2CtradeNbr%3DYD201810172010441001%2CtotalAmount%3D0.01%22%2C%22out_trade_no%22%3A%22YD201810172010441001%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22test%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2Fwww.xxx.com%2Falipay%2Fnotify_url.do&return_url=http%3A%2F%2Fwww.xxx.com%2Falipay%2Freturn_url.do&sign=hKkljBSjxEZHWPnIQtyUuUBRYrG%2Fr6rg0XY0mqMnzotJwyxQ67lyvKOX1q%2BUHNW9aUJngVnvVstflBcl1iKe9AQHj2ZzE3OuV8O2NovvzxNwoTo53tJCFllMlDKfcdgv7G1ZmWsZmjB8TMBUTRarveKArGehc0nbqkdTDEqL%2Ft1Fcu5OTQLLSgMhNMKDloLkchcpvcoFpsttieAMv%2FfnMzXho1dG5cPqAU5V4bHDESC%2FMvA54%2FeOZrJ7ot9C1iLQDgywdjJJP%2F3pANCIMmo%2BSILBmcP1WN5lagNOzypvysBGNozQGuSgozawN6muAhKdVC350ihYgxmxrQfvWsxpgw%3D%3D&sign_type=RSA2×tamp=2018-10-17+20%3A10%3A44&version=1.0";

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(SettingsActivity.this);
                Map<String, String> result = alipay.payV2(orderStr, true);
                for (String s : result.values()) {
                    MyLogger.e("result.values = =" + s);
                }

                Message message = Message.obtain();
                message.what = 1;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        }).start();*/
    }
}
