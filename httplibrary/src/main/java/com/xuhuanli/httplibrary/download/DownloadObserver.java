package com.xuhuanli.httplibrary.download;

import android.app.Dialog;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public abstract class DownloadObserver extends BaseDownloadObserver {

    private String fileName;
    private Dialog mProgressDialog;

    public DownloadObserver(String fileName) {
        this.fileName = fileName;
    }

    public DownloadObserver(String fileName, Dialog mProgressDialog) {
        this.fileName = fileName;
        this.mProgressDialog = mProgressDialog;
    }

    protected abstract void getDisposable(Disposable d);

    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param filePath filePath
     */
    protected abstract void onSuccess(long bytesRead, long contentLength, float progress, boolean done, String filePath);


    @Override
    protected void doOnError(String errorMsg) {
        onError(errorMsg);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        getDisposable(d);
    }

    @Override
    public void onNext(@NonNull ResponseBody responseBody) {
        Observable
                .just(responseBody)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        try {
                            new DownloadManager().saveFile(responseBody, fileName, new ProgressListener() {
                                @Override
                                public void onResponseProgress(final long bytesRead, final long contentLength, final int progress, final boolean done, final String filePath) {
                                    Observable
                                            .just(progress)
                                            .distinctUntilChanged()
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Consumer<Integer>() {
                                                @Override
                                                public void accept(@NonNull Integer integer) throws Exception {
                                                    onSuccess(bytesRead, contentLength, progress, done, filePath);
                                                }
                                            });
                                }

                            });

                        } catch (IOException e) {
                            doOnError(e.getMessage());
                        }
                    }
                });

    }

    @Override
    public void onComplete() {

    }
}
