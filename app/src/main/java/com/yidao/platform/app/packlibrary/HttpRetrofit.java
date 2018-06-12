package com.yidao.platform.app.packlibrary;

import com.yidao.platform.app.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Http retrofit.
 */
@Deprecated
public class HttpRetrofit {

    private static List<Disposable> disposables;

    private HttpRetrofit() {
        disposables = new ArrayList<>();
    }

    public Retrofit creatRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static class HttpRetrofitHolder {
        private static final HttpRetrofit mInstance = new HttpRetrofit();
    }

    /**
     * Add disposable.
     *
     * @param disposable the disposable
     */
    public static void addDisposable(Disposable disposable) {
        if (disposables != null) {
            disposables.add(disposable);
        }
    }

    /**
     * Cancel all request.
     */
    public static void cancelAllRequest() {
        if (disposables != null) {
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
            disposables.clear();
        }
    }

    /**
     * Cancel single request.
     *
     * @param disposable the disposable
     */
    public static void cancelSingleRequest(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * Gets retrofit.
     *
     * @return the retrofit
     */
    public static HttpRetrofit getHttpRetrofit() {
        return HttpRetrofitHolder.mInstance;
    }
}
