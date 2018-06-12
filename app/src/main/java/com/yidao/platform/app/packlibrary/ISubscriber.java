package com.yidao.platform.app.packlibrary;

import io.reactivex.disposables.Disposable;

@Deprecated
public interface ISubscriber<T> {

    void doOnSubscribe(Disposable d);

    void doOnError(String errorMsg);

    void doOnNext(T t);

    void doOnCompleted();
}