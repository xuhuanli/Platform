package com.yidao.platform.contacts.im;

import android.net.Uri;
import android.text.TextUtils;

import com.allen.library.RxHttpUtils;
import com.yidao.platform.app.MyApplicationLike;

import io.objectbox.query.Query;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class UserInfoManager {

    private Query<UserData> infoQuery;

    private UserInfoManager() {
    }

    public static UserInfoManager getInstance() {
        return UserInfoManagerHolder.sInstance;
    }

    public void getUserData(String s) {
        if (TextUtils.isEmpty(s)) {
            return ;
        }
        infoQuery = MyApplicationLike.getBoxStore().boxFor(UserData.class).query().equal(UserData_.userId, s).build();
        UserData userData = infoQuery.findUnique();
        if (userData != null) {
            UserInfo userInfo = new UserInfo(userData.getUserId(), userData.getUserName(), Uri.parse(userData.getPortraitUri()));
            RongIM.getInstance().refreshUserInfoCache(userInfo);
            return ;
        }
        return ;
    }

    private static class UserInfoManagerHolder {
        private static final UserInfoManager sInstance = new UserInfoManager();
    }
}
