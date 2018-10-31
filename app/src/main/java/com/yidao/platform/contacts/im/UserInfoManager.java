package com.yidao.platform.contacts.im;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.MyApplicationLike;
import com.yidao.platform.app.MyObserver.BaseResult;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.contacts.bean.IMTokenInfo;

import io.objectbox.Box;
import io.objectbox.query.Query;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class UserInfoManager {

    private Query<UserData> infoQuery;
    private Box<UserData> box;

    private UserInfoManager() {
    }

    public static UserInfoManager getInstance() {
        return UserInfoManagerHolder.sInstance;
    }

    public void getUserData(Context context, String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        box = MyApplicationLike.getBoxStore().boxFor(UserData.class);
        infoQuery = box.query().equal(UserData_.userId, s).build();
        UserData userData = infoQuery.findUnique();
        if (userData != null) {
            MyLogger.e("userData 在表里");
            UserInfo userInfo = new UserInfo(userData.getUserId(), userData.getUserName(), Uri.parse(userData.getPortraitUri()));
            RongIM.getInstance().refreshUserInfoCache(userInfo);
            return;
        }
        MyLogger.e("userData 不在表里");
        RxHttpUtils
                .createApi(ApiService.class)
                .requestIMToken(s, 0)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseResult<IMTokenInfo>>() {
                    @Override
                    protected void onError(String s) {

                    }

                    @Override
                    protected void onSuccess(BaseResult<IMTokenInfo> imTokenInfoBaseResult) {
                        IMTokenInfo result = imTokenInfoBaseResult.getResult();
                        UserInfo userInfo = new UserInfo(result.getUserId(), result.getName(), Uri.parse(result.getHeadImg()));
                        UserData data = new UserData();
                        data.setUserId(result.getUserId());
                        data.setUserName(result.getName());
                        data.setPortraitUri(result.getHeadImg());
                        box.put(data);
                        RongIM.getInstance().refreshUserInfoCache(userInfo);
                        return;
                    }
                });
        return;
    }

    private static class UserInfoManagerHolder {
        private static final UserInfoManager sInstance = new UserInfoManager();
    }
}
