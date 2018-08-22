package com.yidao.platform.login.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.login.model.BindPhoneObj;
import com.yidao.platform.login.view.IViewBindingPhoneActivity;

import org.json.JSONException;
import org.json.JSONObject;

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

    /**
     * 绑定手机号
     */
    public void bindPhone(BindPhoneObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .bindPhone(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        MyLogger.d("bind phone 13645876734 ：："+data);
                        try {
                            JSONObject object = new JSONObject(data);
                            // TODO: 2018/8/21 0021 昨天的工作位置
                            //object.get("errorCode")
                            mView.bindSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
