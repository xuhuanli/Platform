package com.yidao.platform.app.packlibrary;

import android.app.Dialog;

import io.reactivex.disposables.Disposable;

@Deprecated
public abstract class DataObserver<T> extends BaseDataObserver<T> {

    public DataObserver() {
    }

    public DataObserver(Dialog progressDialog) {
    }

    protected abstract void onSuccess(T data);

    protected abstract void onError(String msg);

    @Override
    public void doOnSubscribe(Disposable d) {
        HttpRetrofit.addDisposable(d);
    }

    @Override
    public void doOnError(String errorMsg) {
        onError(errorMsg);
    }

    @Override
    public void doOnNext(T data) {
        onSuccess(data);
    }

    @Override
    public void doOnCompleted() {
        // TODO: 2018/6/12 0012 可以在这里添加dialog的dismiss
    }
}
