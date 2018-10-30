package com.yidao.platform.app.MyObserver;

import com.allen.library.exception.ApiException;
import com.allen.library.manage.RxHttpManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseAppObserver<T> implements Observer<BaseResult<T>>, IAppSubscriber<T> {
    protected static final String HTTP_ERROR = "0";

    /**
     * 是否隐藏toast
     *
     * @return boolean
     */
    protected boolean isHideToast() {
        return false;
    }

    /**
     * 标记网络请求的tag
     * tag下的一组或一个请求，用来处理一个页面的所以请求或者某个请求
     * 设置一个tag就行就可以取消当前页面所有请求或者某个请求了
     *
     * @return string
     */
    protected String setTag() {
        return null;
    }

    @Override
    public void onSubscribe(Disposable d) {
        RxHttpManager.get().add(setTag(), d);
        doOnSubscribe(d);
    }

    @Override
    public void onNext(BaseResult<T> baseResult) {
        doOnNext(baseResult);
    }

    @Override
    public void onError(Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }

    private void setError(String errorMsg) {
        doOnError(HTTP_ERROR, errorMsg);
    }
}
