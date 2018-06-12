package com.yidao.platform.app.packlibrary;

import io.reactivex.disposables.Disposable;

//instead of ISubscriber
@Deprecated
public interface IStringSubscriber {

    void doOnSubscribe(Disposable d);

    void doOnError(String errorMsg);

    void doOnNext(String string);

    void doOnCompleted();
}
