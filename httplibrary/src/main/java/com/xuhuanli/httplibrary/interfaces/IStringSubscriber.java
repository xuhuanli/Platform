package com.xuhuanli.httplibrary.interfaces;

import io.reactivex.disposables.Disposable;

public interface IStringSubscriber {

    void doOnSubscribe(Disposable d);

    void doOnError(String errorMsg);

    void doOnNext(String string);

    void doOnCompleted();
}
