package com.yidao.platform.login.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.login.bean.WxCodeBean;
import com.yidao.platform.login.model.LoginObj;
import com.yidao.platform.login.view.IViewLoginActivity;

public class LoginPresenter {
    private IViewLoginActivity mView;

    public LoginPresenter(IViewLoginActivity mView) {
        this.mView = mView;
    }

    /**
     * 请求验证码
     *
     * @param phone
     */
    public void requestVCode(String phone) {
        RxHttpUtils
                .createApi(ApiService.class)
                .sendVCode(phone)
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
     * 手机登录
     *
     * @param obj
     */
    public void phoneSignIn(LoginObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .phoneSignIn(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<WxCodeBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(WxCodeBean wxCodeBean) {
                        switch (wxCodeBean.getErrCode()) {
                            case "1000":
                                mView.bindSuccess(wxCodeBean.getResult());
                                break;
                            case "1031": //验证码已失效
                            case "1050"://非法手机号
                                mView.showInfo(wxCodeBean.getInfo());
                                break;
                            case "1052": //手机号码已经过微信
                                mView.hasBindToWx();
                                break;
                            case "1053": //手机号未注册
                                mView.needRegister();
                                break;
                        }
                    }
                });
    }

    /**
     * 注册
     *
     * @param obj
     */
    public void registerAccount(LoginObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .registerAccount(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<WxCodeBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(WxCodeBean wxCodeBean) {
                        switch (wxCodeBean.getErrCode()) {
                            case "1000":
                                mView.bindSuccess(wxCodeBean.getResult());
                                break;
                            case "1031": //验证码已失效
                            case "1050"://非法手机号
                                mView.showInfo(wxCodeBean.getInfo());
                                break;
                            case "1052": //手机号码已经过微信
                                mView.hasBindToWx();
                                break;
                        }
                    }
                });
    }
}
