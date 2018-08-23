package com.yidao.platform.app.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.allen.library.RxHttpUtils;
import com.umeng.analytics.MobclickAgent;
import com.yidao.platform.app.utils.MyLogger;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * The type Base activity.
 *
 * @author xuhuanli
 */
public abstract class BaseActivity extends AppCompatActivity {

    public CompositeDisposable mCompositeDisposable;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
    }

    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * Start activity.
     *
     * @param clz the clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearDisposable();
        RxHttpUtils.clearAllCompositeDisposable();
        mUnbinder.unbind();
    }

    /**
     * Start activity.
     *
     * @param clz    the clz
     * @param bundle the bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected abstract int getLayoutId();
}
