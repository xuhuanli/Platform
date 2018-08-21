package com.yidao.platform.wxapi;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.login.bean.WxCodeBean;

import java.util.HashMap;

public class WXEntryActivityPresenter {
    private IViewWXEntryActivity mView;

    public WXEntryActivityPresenter(IViewWXEntryActivity mView) {
        this.mView = mView;
    }

    /**
     * 传微信code到服务器
     *
     * @param code
     * @param deviceId
     * @param android
     */
    public void sendCodeToServer(String code, String deviceId, String android) {
        HashMap<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("deviceId", deviceId);
        map.put("deviceType", android);
        RxHttpUtils
                .createApi(ApiService.class)
                .sendCodeToServer(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<WxCodeBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                        mView.loginFail();
                    }

                    @Override
                    protected void onSuccess(WxCodeBean wxCodeBean) {
                        switch (wxCodeBean.getErrCode()) {
                            case "1000":
                                WxCodeBean.ResultBean result = wxCodeBean.getResult();
                                mView.loginSuccess(result);
                                break;
                            default:
                                mView.loginFail();
                                break;
                        }
                    }
                });
    }
}
