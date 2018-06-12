package com.xuhuanli.httplibrary.interfaces;

import io.reactivex.disposables.Disposable;

public interface ISubscriber<T> {

    void doOnSubscribe(Disposable d);

    void doOnError(String errorMsg);

    void doOnNext(T t);

    void doOnCompleted();
}
