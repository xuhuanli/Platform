package com.xuhuanli.httplibrary.observer;


import android.app.Dialog;

import com.xuhuanli.httplibrary.MyRetrofit;
import com.xuhuanli.httplibrary.base.BaseObserver;
import com.xuhuanli.httplibrary.utils.ToastUtils;

import io.reactivex.disposables.Disposable;

public abstract class CommonObserver<T> extends BaseObserver<T> {


    private Dialog mProgressDialog;

    public CommonObserver() {
    }

    public CommonObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    protected abstract void onError(String errorMsg);

    protected abstract void onSuccess(T t);



    @Override
    public void doOnSubscribe(Disposable d) {
        MyRetrofit.addDisposable(d);
    }

    @Override
    public void doOnError(String errorMsg) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        if (!isHideToast()) {
            ToastUtils.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOnCompleted() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
