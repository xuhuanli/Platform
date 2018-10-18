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

import com.alipay.sdk.app.PayTask;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding2.view.RxView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.ThreadPoolManager;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.FileUtil;
import com.yidao.platform.app.utils.MyLogger;
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
        String orderStr = "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018101261694052&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E5%93%A6%22%2C%22out_trade_no%22%3A%22YD201810181333161001%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%94%AF%E4%BB%98%E5%AE%9D%E6%B5%8B%E8%AF%95%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&sign=LSgaLjeeaxf103JaR%2B2zAwsyGXGvKiKkjKRoOITR5MDJOCjwqQFou9zb3F8Qcxtav4%2FcTIWLmulgPw0I7bcmTv%2B7MdGnMSdLJOiDlSKAoZUa0ObSkEMBzMCcjOxXkMtYS8GliFA04Ebn7%2F77HgUZkEkxGcZ2SNWhznTyK46Mz6%2BDgMCPcEZPpR%2BF8HV1dkWEXHx%2FHuwsGI5J4PciyzmLZok64L6RcVbnt2e9WyxyRr6cOKodeRmH7hOMwhdER2xH5TLvSQ1%2BTI1OEJe0g93PJxSrVgZcZOEQdsS2h5UP9ObTZBafFz2huof%2BqpYkceP3g3MmCYbuEs%2BCTSPR5mntaA%3D%3D&sign_type=RSA2&timestamp=2018-10-18+13%3A33%3A16&version=1.0";

        new Thread(() -> {
            PayTask alipay = new PayTask(SettingsActivity.this);
            Map<String, String> result = alipay.payV2(orderStr, true);
            for (String s : result.values()) {
                MyLogger.e("result.values = =" + s);
            }

            Message message = Message.obtain();
            message.what = 1;
            message.obj = result;
            mHandler.sendMessage(message);
        }).start();
    }
}
