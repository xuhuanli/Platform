package com.yidao.platform.app.utils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Token拦截器
 */
public class TokenInterceptor implements Interceptor {
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Response originalResponse = chain.proceed(originalRequest);
        // 获取返回的数据字符串
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = originalResponse.body().source();
        source.request(Integer.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF_8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset();
        }
        String bodyString = buffer.clone().readString(charset);
        MyLogger.i("http code",bodyString);
        // 如果 token 已经过期
        /*if (token is expired){
            String newToken = service.refreshToken.execute().body();
            // 保存新的 token
            Config.saveToken(newToken);
            // 添加到 Query 参数
            HttpUrl url = chain.request().url()
                    .newBuilder()
                    .setQueryParameter("Token", newToken)
                    .build();
            Request newRequest = chain.request().newBuilder()
                    .url(url)
                    .build();
            // 添加到 Header
            Request newRequest = originalRequest.newBuilder()
                    .header("Authorization", newToken)
                    .build();
            originalResponse.body().close();
            return chain.proceed(newRequest);
        }*/
        return originalResponse;
    }
}
