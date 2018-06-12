package com.xuhuanli.httplibrary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.xuhuanli.httplibrary.constant.SPKeys;
import com.xuhuanli.httplibrary.download.DownloadRetrofit;
import com.xuhuanli.httplibrary.http.GlobalRxHttp;
import com.xuhuanli.httplibrary.http.SingleRxHttp;
import com.xuhuanli.httplibrary.upload.UploadRetrofit;
import com.xuhuanli.httplibrary.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class MyRetrofit {

    @SuppressLint("StaticFieldLeak")
    private static MyRetrofit instance;
    @SuppressLint("StaticFieldLeak")
    private static Application context;

    private static List<Disposable> disposables;

    private static String networkData;

    public static MyRetrofit getInstance() {
        checkInitialize();
        if (instance == null) {
            synchronized (MyRetrofit.class) {
                if (instance == null) {
                    instance = new MyRetrofit();
                    disposables = new ArrayList<>();
                }
            }

        }
        return instance;
    }


    public static void init(Application app) {
        context = app;
    }

    public static Context getContext() {
        checkInitialize();
        return context;
    }

    private static void checkInitialize() {
        if (context == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 MyRetrofit.init() 初始化！");
        }
    }


    public GlobalRxHttp config() {
        return GlobalRxHttp.getInstance();
    }


    public static <K> K createApi(Class<K> cls) {
        return GlobalRxHttp.createGApi(cls);
    }

    public static SingleRxHttp getSInstance() {

        return SingleRxHttp.getInstance();
    }


    public static Observable<ResponseBody> downloadFile(String fileUrl) {
        return DownloadRetrofit.downloadFile(fileUrl);
    }

    public static Observable<ResponseBody> uploadImg(String uploadUrl, String filePath) {
        return UploadRetrofit.uploadImg(uploadUrl, filePath);
    }

    public static Observable<ResponseBody> uploadImgs(String uploadUrl, List<String> filePaths) {
        return UploadRetrofit.uploadImgs(uploadUrl, filePaths);
    }

    public static HashSet<String> getCookie() {
        HashSet<String> preferences = (HashSet<String>) SPUtils.get(SPKeys.COOKIE, new HashSet<String>());
        return preferences;
    }

    public static void addDisposable(Disposable disposable) {
        if (disposables != null) {
            disposables.add(disposable);
        }
    }

    public static void cancelAllRequest() {
        if (disposables != null) {
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
            disposables.clear();
        }
    }

    public static void cancelSingleRequest(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
