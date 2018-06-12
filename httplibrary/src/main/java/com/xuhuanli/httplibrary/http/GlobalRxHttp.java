package com.xuhuanli.httplibrary.http;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.xuhuanli.httplibrary.interceptor.AddCookiesInterceptor;
import com.xuhuanli.httplibrary.interceptor.CacheInterceptor;
import com.xuhuanli.httplibrary.interceptor.HeaderInterceptor;
import com.xuhuanli.httplibrary.interceptor.ReceivedCookiesInterceptor;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class GlobalRxHttp {

    private static GlobalRxHttp instance;

    public static GlobalRxHttp getInstance() {

        if (instance == null) {
            synchronized (GlobalRxHttp.class) {
                if (instance == null) {
                    instance = new GlobalRxHttp();
                }
            }

        }
        return instance;
    }

    public GlobalRxHttp setBaseUrl(String baseUrl) {
        getGlobalRetrofitBuilder().baseUrl(baseUrl);
        return this;
    }


    public GlobalRxHttp setOkClient(OkHttpClient okClient) {
        getGlobalRetrofitBuilder().client(okClient);
        return this;
    }


    public GlobalRxHttp setHeaders(Map<String, Object> headerMaps) {
        getGlobalOkHttpBuilder().addInterceptor(new HeaderInterceptor(headerMaps));
        return this;
    }

    public GlobalRxHttp setLog(boolean isShowLog) {
        if (isShowLog) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e("MyRetrofit", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            getGlobalOkHttpBuilder().addInterceptor(loggingInterceptor);
        }
        return this;
    }

    public GlobalRxHttp setCache() {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        Cache cache = new Cache(new File(Environment.getExternalStorageDirectory().getPath() + "/rxHttpCacheData")
                , 1024 * 1024 * 100);
        getGlobalOkHttpBuilder().addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache);
        return this;
    }

    public GlobalRxHttp setCache(String cachePath, long maxSize) {
        if (!TextUtils.isEmpty(cachePath) && maxSize > 0) {
            CacheInterceptor cacheInterceptor = new CacheInterceptor();
            Cache cache = new Cache(new File(cachePath), maxSize);
            getGlobalOkHttpBuilder()
                    .addInterceptor(cacheInterceptor)
                    .addNetworkInterceptor(cacheInterceptor)
                    .cache(cache);
        }

        return this;
    }

    public GlobalRxHttp setCookie(boolean saveCookie) {
        if (saveCookie) {
            getGlobalOkHttpBuilder()
                    .addInterceptor(new AddCookiesInterceptor())
                    .addInterceptor(new ReceivedCookiesInterceptor());
        }
        return this;
    }


    public GlobalRxHttp setReadTimeout(long second) {
        getGlobalOkHttpBuilder().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }

    public GlobalRxHttp setWriteTimeout(long second) {
        getGlobalOkHttpBuilder().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }

    public GlobalRxHttp setConnectTimeout(long second) {
        getGlobalOkHttpBuilder().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }

    public GlobalRxHttp setSslSocketFactory() {
        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory();
        getGlobalOkHttpBuilder().sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }

    public GlobalRxHttp setSslSocketFactory(InputStream... certificates) {
        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory(certificates);
        getGlobalOkHttpBuilder().sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }

    public GlobalRxHttp setSslSocketFactory(InputStream bksFile, String password, InputStream... certificates) {
        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory(bksFile, password, certificates);
        getGlobalOkHttpBuilder().sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }


    public static Retrofit getGlobalRetrofit() {
        return RetrofitClient.getInstance().getRetrofit();
    }

    public Retrofit.Builder getGlobalRetrofitBuilder() {
        return RetrofitClient.getInstance().getRetrofitBuilder();
    }

    public OkHttpClient.Builder getGlobalOkHttpBuilder() {
        return HttpClient.getInstance().getBuilder();
    }

    public static <K> K createGApi(final Class<K> cls) {
        return getGlobalRetrofit().create(cls);
    }


}
