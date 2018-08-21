package com.yidao.platform.login.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.login.view.IViewBindingPhoneActivity;

public class LoginBindingPhonePresenter {
    private IViewBindingPhoneActivity mView;

    public LoginBindingPhonePresenter(IViewBindingPhoneActivity mView) {
        this.mView = mView;
    }

    public void requestVCode(String phone, String userId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .sendVCode(phone, userId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        mView.sendCodeSuccess();
                    }
                });
    }
}
