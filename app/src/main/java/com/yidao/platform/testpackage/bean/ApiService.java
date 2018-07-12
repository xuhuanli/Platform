package com.yidao.platform.testpackage.bean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("product/style/get/1014051050182672384")
    Observable<TestBean> getGod();
}
