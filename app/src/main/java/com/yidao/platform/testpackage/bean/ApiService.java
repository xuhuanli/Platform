package com.yidao.platform.testpackage.bean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("product/style/get/1014051050182672384")
    Observable<TestBean> getGod();

    @GET("sns/oauth2/access_token")
    Observable<WxTokenBean> getWxToken(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code, @Query("grant_type") String grant_type);

    @GET("sns/userinfo")
    Observable<UserDataBean> getUserInfo(@Query("access_token") String access_token, @Query("openid") String openid);
}
