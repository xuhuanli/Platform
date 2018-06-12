package com.xuhuanli.httplibrary.download;

import com.xuhuanli.httplibrary.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import okhttp3.ResponseBody;

import static com.xuhuanli.httplibrary.utils.ToastUtils.showToast;

public abstract class BaseDownloadObserver implements Observer<ResponseBody> {

    protected abstract void doOnError(String errorMsg);


    @Override
    public void onError(@NonNull Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(error);
    }

    private void setError(String errorMsg) {
        showToast(errorMsg);
        doOnError(errorMsg);
    }

}
