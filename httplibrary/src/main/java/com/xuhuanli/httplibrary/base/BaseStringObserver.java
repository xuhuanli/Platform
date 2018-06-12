package com.xuhuanli.httplibrary.base;

import com.xuhuanli.httplibrary.exception.ApiException;
import com.xuhuanli.httplibrary.interfaces.IStringSubscriber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseStringObserver implements Observer<String>, IStringSubscriber {

    protected boolean isHideToast() {
        return false;
    }

    @Override
    public void onSubscribe(Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(String string) {
        doOnNext(string);
    }

    @Override
    public void onError(Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        doOnError(error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }

}
