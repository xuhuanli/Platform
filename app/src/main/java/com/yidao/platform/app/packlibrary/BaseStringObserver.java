package com.yidao.platform.app.packlibrary;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@Deprecated
public abstract class BaseStringObserver implements Observer<String>, ISubscriber<String> {

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
        doOnError(e.getMessage());
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }
}
