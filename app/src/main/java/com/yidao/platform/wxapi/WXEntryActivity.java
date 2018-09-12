package com.yidao.platform.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.allen.library.RxHttpUtils;
import com.allen.library.utils.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.MyApplicationLike;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.container.ContainerActivity;
import com.yidao.platform.events.WxSignInEvent;
import com.yidao.platform.login.bean.WxCodeBean;
import com.yidao.platform.login.view.LoginBindingPhoneActivity;

import org.greenrobot.eventbus.EventBus;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler, IViewWXEntryActivity {

    private IWXAPI mWxapi;
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private WXEntryActivityPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new WXEntryActivityPresenter(this);
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
                    case RETURN_MSG_TYPE_LOGIN:  //type = 1 表示登录
                        String code = ((SendAuth.Resp) baseResp).code;
                        String deviceId = IPreference.prefHolder.getPreference(MyApplicationLike.getAppContext()).get(Constant.STRING_DEVICE_ID, IPreference.DataType.STRING);
                        mPresenter.sendCodeToServer(code, deviceId, "Android");
                        break;
                    case RETURN_MSG_TYPE_SHARE:  //type = 2 表示分享
                        //分享成功后结束掉回调activity
                        //((SendAuth.Resp) baseResp)
                        String artId = baseResp.transaction;
                        String userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
                        mPresenter.updateShareData(artId, userId);
                        finish();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWxapi.unregisterApp();
        RxHttpUtils.clearAllCompositeDisposable();
    }

    @Override
    public void loginFail() {
        finish();
        ToastUtils.showToast("登录失败");
    }

    @Override
    public void loginFail(String info) {
        finish();
        ToastUtils.showToast("登录失败: " + info);
    }

    @Override
    public void loginSuccess(WxCodeBean.ResultBean result) {
        MyLogger.e(result.toString());
        IPreference.prefHolder.getPreference(this).put(Constant.STRING_USER_TOKEN, result.getToken());
        IPreference.prefHolder.getPreference(this).put(Constant.STRING_USER_REFRESHTOKEN, result.getRefreshToken());
        EventBus.getDefault().post(new WxSignInEvent());
        if (result.isBindPhone()) {
            //已绑定，需要写入userId
            IPreference.prefHolder.getPreference(this).put(Constant.STRING_USER_ID, result.getUserId());
            startActivity(new Intent(this, ContainerActivity.class));
            finish();
        } else {  //没绑定， 跳转到绑定页 绑定成功后写入userId
            Intent intent = new Intent(this, LoginBindingPhoneActivity.class);
            intent.putExtra(Constant.STRING_USER_ID, result.getUserId());
            startActivity(intent);
            finish();
        }
    }
}
