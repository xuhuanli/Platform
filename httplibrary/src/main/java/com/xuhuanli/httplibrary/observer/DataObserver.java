package com.xuhuanli.httplibrary.observer;

import android.app.Dialog;

import com.xuhuanli.httplibrary.MyRetrofit;
import com.xuhuanli.httplibrary.base.BaseDataObserver;
import com.xuhuanli.httplibrary.bean.BaseData;
import com.xuhuanli.httplibrary.utils.ToastUtils;

import io.reactivex.disposables.Disposable;

public abstract class DataObserver<T> extends BaseDataObserver<T> {

    private Dialog mProgressDialog;

    public DataObserver() {
    }

    public DataObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    protected abstract void onError(String errorMsg);

    protected abstract void onSuccess(T data);

    @Override
    public void doOnSubscribe(Disposable d) {
        MyRetrofit.addDisposable(d);
    }

    @Override
    public void doOnError(String errorMsg) {
        dismissLoading();
        if (!isHideToast()) {
            ToastUtils.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(BaseData<T> data) {
        onSuccess(data.getData());
        //可以根据需求对code统一处理
//        switch (data.getCode()) {
//            case 200:
//                onSuccess(data.getData());
//                break;
//            case 300:
//            case 500:
//                onError(data.getMsg());
//                break;
//            default:
//        }
    }

    @Override
    public void doOnCompleted() {
        dismissLoading();
    }

    private void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
