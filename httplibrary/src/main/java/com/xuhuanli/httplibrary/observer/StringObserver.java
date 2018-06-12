package com.xuhuanli.httplibrary.observer;

import android.app.Dialog;

import com.xuhuanli.httplibrary.MyRetrofit;
import com.xuhuanli.httplibrary.base.BaseStringObserver;
import com.xuhuanli.httplibrary.utils.ToastUtils;

import io.reactivex.disposables.Disposable;


public abstract class StringObserver extends BaseStringObserver {

    private Dialog mProgressDialog;

    public StringObserver() {
    }

    public StringObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    protected abstract void onError(String errorMsg);

    protected abstract void onSuccess(String data);


    @Override
    public void doOnSubscribe(Disposable d) {
        MyRetrofit.addDisposable(d);
    }

    @Override
    public void doOnError(String errorMsg) {
        dismissLoading();
        if (!isHideToast()) {
            ToastUtils.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(String string) {
        onSuccess(string);
    }


    @Override
    public void doOnCompleted() {
        dismissLoading();
    }

    private void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
