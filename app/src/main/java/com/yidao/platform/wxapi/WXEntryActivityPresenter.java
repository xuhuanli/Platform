package com.yidao.platform.wxapi;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;

import java.util.HashMap;

public class WXEntryActivityPresenter {
    private IViewWXEntryActivity mView;

    public WXEntryActivityPresenter(IViewWXEntryActivity mView) {
        this.mView = mView;
    }

    /**
     * 传微信code到服务器
     * @param code
     * @param deviceId
     * @param android
     */
    public void sendCodeToServer(String code, String deviceId, String android) {
        HashMap<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("deviceId", deviceId);
        map.put("deviceType", android);
        RxHttpUtils.getSInstance()
                .baseUrl("http://10.10.20.27:8080/")
                .createSApi(ApiService.class)
                .sendCodeToServer(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.i("weixin",errorMsg);
                        showError();
                    }

                    @Override
                    protected void onSuccess(String data) {
                        MyLogger.i("weixin",data);
                    }
                });
    }

    private void showError() {
        mView.loginFail();
    }
}
