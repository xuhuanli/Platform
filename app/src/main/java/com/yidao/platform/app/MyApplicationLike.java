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
import com.meituan.android.walle.WalleChannelReader;
import com.shujike.analysis.SjkAgent;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.contacts.im.CustomizeMessage;
import com.yidao.platform.contacts.im.CustomizeMessageItemProvider;
import com.yidao.platform.contacts.im.IMListenerWrapper;
import com.yidao.platform.contacts.im.MyObjectBox;
import com.yidao.platform.container.ContainerActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import io.rong.imkit.RongIM;
import okhttp3.OkHttpClient;

public class MyApplicationLike extends DefaultApplicationLike {
    private static final String TAG = "Tinker.MyApplicationLike";
    private static Context appContext;
    private static HashMap<String, Integer> labelMap = new HashMap<>();
    private static BoxStore boxStore;

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
        initRetrofit();
        initCloudChannel(appContext);
        initBugly();
        //initLeakCanary();
        initLogger();
        initUmengAnalytics(appContext);
        initIM();
        setLabelMap();
        initBox();
        initSjk();
    }

    private void initBox() {
        boxStore = MyObjectBox.builder().androidContext(getAppContext()).build();
    }

    public static BoxStore getBoxStore() {
        return boxStore;
    }

    private void initIM() {
        RongIM.init(getAppContext());
        IMListenerWrapper.init(getAppContext());
        RongIM.registerMessageType(CustomizeMessage.class);
        RongIM.registerMessageTemplate(new CustomizeMessageItemProvider());
        /*RongIM.connect(IM_TOKEN, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                MyLogger.e("onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                MyLogger.e("onSuccess userId is " + s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                MyLogger.e("onError");
            }
        });*/
    }

    private void initSjk() {
        SjkAgent.setDebugEnabled(Constant.IS_DEBUG);//正式发版时请关闭debug模式
        SjkAgent.init(appContext);
    }

    private void setLabelMap() {
        labelMap.put("商业计划书", 1);
        labelMap.put("产品原型", 2);
        labelMap.put("技术开发", 3);
        labelMap.put("投融资需求", 4);
        labelMap.put("资源对接", 5);
        labelMap.put("路演/峰会", 6);
        labelMap.put("项目投资", 7);
        labelMap.put("项目评估", 8);
        labelMap.put("其它", 9);
    }

    public static int getLabelId(String label) {
        return labelMap.get(label);
    }

    //初始化友盟统计
    private void initUmengAnalytics(Context context) {
        String channel = WalleChannelReader.getChannel(context);
        UMConfigure.init(context, Constant.UMENG_APPKEY, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(Constant.IS_DEBUG);
        PlatformConfig.setWeixin(Constant.WX_LOGIN_APP_ID, "d0ec01859e2f3b97f3deff38682cb181");
    }

    private void initBugly() {
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
        return new OkHttpConfig
                .Builder(getAppContext())
                //.setHeaders(headerMaps)
                //.setCachePath(FileUtil.getDiskCacheDir(getAppContext(), "http_cache").getPath())
                //开启缓存策略(默认false)
                //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                //2、在没有网络的时候，去读缓存中的数据。
                .setCache(false)
                //.setHeaders(headers)
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
                    EventBus.getDefault().post(new DeviceIdEvent());
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
