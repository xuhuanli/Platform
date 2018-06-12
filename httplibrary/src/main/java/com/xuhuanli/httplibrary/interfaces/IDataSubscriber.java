package com.xuhuanli.httplibrary.interfaces;

import com.xuhuanli.httplibrary.bean.BaseData;

import io.reactivex.disposables.Disposable;

public interface IDataSubscriber<T> {

    void doOnSubscribe(Disposable d);

    void doOnError(String errorMsg);

    void doOnNext(BaseData<T> baseData);

    void doOnCompleted();
}
