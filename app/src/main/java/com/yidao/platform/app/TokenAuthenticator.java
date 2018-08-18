package com.yidao.platform.app;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        // 去获取新的 token，采用同步请求方式
        //String newToken = service.refreshToken.execute().body();
        // 保存新的 token
        return response.request().newBuilder()
                .addHeader("Authorization","newToken")
                .build();
    }
}
