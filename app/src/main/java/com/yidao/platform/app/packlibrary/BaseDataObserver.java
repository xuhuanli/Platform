package com.yidao.platform.app.packlibrary;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * instead of httplibrary
 */
@Deprecated
public abstract class BaseDataObserver<T> implements Observer<T>, ISubscriber<T> {

    @Override
    public void onSubscribe(Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(T t) {
        doOnNext(t);
    }

    @Override
    public void onError(Throwable e) {
        doOnError(e.getMessage());
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }
}
