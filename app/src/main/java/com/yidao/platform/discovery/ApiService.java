package com.yidao.platform.discovery;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("blog/{id}")
    Observable<Reception> getBlog(@Path("id") int id);
}
