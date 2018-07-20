package com.yidao.platform.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.login.LoginClassificationActivity;
import com.yidao.platform.testpackage.bean.ApiService;
import com.yidao.platform.testpackage.bean.UserDataBean;
import com.yidao.platform.testpackage.bean.WxTokenBean;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI mWxapi;
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWxapi = WXAPIFactory.createWXAPI(this, Constant.WX_LOGIN_APP_ID, Constant.IS_DEBUG);
        mWxapi.handleIntent(getIntent(), this);
    }

    //微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法 登录回调此处
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) baseResp).code;
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        RxHttpUtils
                                .getSInstance()
                                .baseUrl("https://api.weixin.qq.com/")
                                .createSApi(ApiService.class)
                                .getWxToken(Constant.WX_LOGIN_APP_ID, Constant.WX_LOGIN_APP_SECRET, code, "authorization_code")
                                .compose(Transformer.<WxTokenBean>switchSchedulers())
                                .subscribe(new CommonObserver<WxTokenBean>() {
                                    @Override
                                    protected void onError(String errorMsg) {
                                        MyLogger.d(errorMsg);
                                        finish();
                                    }

                                    @Override
                                    protected void onSuccess(WxTokenBean wxTokenBean) {
                                        MyLogger.d("success"+wxTokenBean.getErrmsg()+"------"+wxTokenBean.getErrcode());
                                        if (wxTokenBean.getErrmsg() == null) {
                                            String access_token = wxTokenBean.getAccess_token();
                                            String openid = wxTokenBean.getOpenid();
                                            MyLogger.d(access_token);
                                            requestUserInfo(access_token, openid);
                                        }
                                    }
                                });
                        MyLogger.d(code);
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        //分享成功后结束掉回调activity
                        finish();
                        break;
                }
                break;
        }
    }

    /**
     * 获取用户信息
     */
    private void requestUserInfo(String access_token, String openid) {
        RxHttpUtils
                .getSInstance()
                .baseUrl("https://api.weixin.qq.com/")
                .createSApi(ApiService.class)
                .getUserInfo(access_token, openid)
                .compose(Transformer.<UserDataBean>switchSchedulers())
                .subscribe(new CommonObserver<UserDataBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(UserDataBean userDataBean) {
                        if (userDataBean.getErrmsg() == null) {
                            MyLogger.d(userDataBean.getHeadimgurl());
                            Intent intent = new Intent(WXEntryActivity.this, LoginClassificationActivity.class);
                            startActivity(intent);
                        }
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWxapi.unregisterApp();
        RxHttpUtils.clearAllCompositeDisposable();
    }
}
