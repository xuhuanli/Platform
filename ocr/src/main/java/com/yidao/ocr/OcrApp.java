package com.yidao.ocr;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.allen.library.RxHttpUtils;
import com.allen.library.config.OkHttpConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import okhttp3.OkHttpClient;

public class OcrApp extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initRetrofit();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getContext(){
        return context;
    }

    private void initRetrofit() {
        OkHttpClient okHttpClient = initOkHttpClient();
        RxHttpUtils
                .getInstance()
                .init(this)
                .config()
                //配置全局baseUrl
                .setBaseUrl("http://10.10.20.13:8080/")
                //开启全局配置
                .setOkClient(okHttpClient);
    }

    private OkHttpClient initOkHttpClient() {
        return new OkHttpConfig
                .Builder()
                //.setHeaders(headerMaps)
                //.setCachePath(FileUtil.getDiskCacheDir(getAppContext(), "http_cache").getPath())
                //开启缓存策略(默认false)
                //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                //2、在没有网络的时候，去读缓存中的数据。
                .setCache(false)
                //.setHeaders(headers)
                //全局持久话cookie,保存本地每次都会携带在header中（默认false）
                .setSaveCookie(false)
                //.setAddInterceptor(new TokenInterceptor())
                //全局ssl证书认证
                //1、信任所有证书,不安全有风险（默认信任所有证书）
                //.setSslSocketFactory()
                //2、使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(cerInputStream)
                //3、使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(bksInputStream,"123456",cerInputStream)
                //全局超时配置
                .setReadTimeout(10)
                //全局超时配置
                .setWriteTimeout(10)
                //全局超时配置
                .setConnectTimeout(10)
                //全局是否打开请求log日志
                .setDebug(true)
                .build();
    }
}
