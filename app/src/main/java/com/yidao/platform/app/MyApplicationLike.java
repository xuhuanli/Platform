package com.yidao.platform.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.allen.library.RxHttpUtils;
import com.allen.library.config.OkHttpConfig;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.commonsdk.UMConfigure;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.app.utils.FileUtil;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.container.ContainerActivity;

import okhttp3.OkHttpClient;

public class MyApplicationLike extends DefaultApplicationLike {
    public static final String TAG = "Tinker.MyApplicationLike";
    private static Context appContext;

    public MyApplicationLike(Application application, int tinkerFlags,
                             boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                             long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        appContext = getApplication();
        //为了尽快拿到设备ID回调，请把阿里云推送放到最前面
        initCloudChannel(appContext);
        initRetrofit();
        initBugly();
        initLeakCanary();
        initLogger();
        initUmengAnalytics(appContext);
    }

    //初始化友盟统计
    private void initUmengAnalytics(Context context) {
        UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, "");
        //umeng调试开关，打包的时候记得此处设为false
        UMConfigure.setLogEnabled(true);
    }

    private void initBugly() {
        //设置当前apk属于开发设备
        //Bugly.setIsDevelopmentDevice(getAppContext(),true);
        Bugly.init(getApplication(), Constant.BUGLY_ID, Constant.IS_DEBUG);
        Beta.canShowUpgradeActs.add(ContainerActivity.class);
    }

    private void initLogger() {
        MyLogger.initLogger();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(getApplication())) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());
    }

    private void initRetrofit() {
        OkHttpClient okHttpClient = initOkHttpClient();
        RxHttpUtils
                .getInstance()
                .init(getApplication())
                .config()
                //配置全局baseUrl
                .setBaseUrl(Constant.BASE_URL)
                //开启全局配置
                .setOkClient(okHttpClient);
    }

    private OkHttpClient initOkHttpClient() {
        //HashMap<String, Object> headers = new HashMap<>();
        //headers.put("token", IPreference.prefHolder.getPreference(getAppContext()).getString(Constant.STRING_USER_TOKEN));
        OkHttpClient okHttpClient = new OkHttpConfig
                .Builder()
                //全局的请求头信息
                //.setHeaders(headerMaps)
                .setCachePath(FileUtil.getDiskCacheDir(getAppContext(), "http_cache").getPath())
                //开启缓存策略(默认false)
                //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                //2、在没有网络的时候，去读缓存中的数据。
                .setCache(false)
                //.setHeaders(headers)
                //全局持久话cookie,保存本地每次都会携带在header中（默认false）
                .setSaveCookie(false)
                //添加自己的拦截器 基于DiskLruCache
                //.setAddInterceptor(new MyCacheInterceptor())
                .setAddInterceptor(new TokenInterceptor())
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
                .setDebug(Constant.IS_DEBUG)
                .build();
        return okHttpClient;
    }

    /**
     * 初始化aliyun push通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response) {
                String deviceId = pushService.getDeviceId();
                MyLogger.e("当前设备对应的deviceId是-->" + deviceId);
                if (!IPreference.prefHolder.getPreference(getAppContext()).contains(Constant.STRING_DEVICE_ID)) {
                    IPreference.prefHolder.getPreference(getAppContext()).put(Constant.STRING_DEVICE_ID, deviceId);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.i(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    public static Context getAppContext() {
        return appContext;
    }
}
