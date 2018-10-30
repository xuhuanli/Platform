package com.yidao.platform.app.MyObserver;

import io.reactivex.disposables.Disposable;

public interface IAppSubscriber<T> {
    /**
     * doOnSubscribe 回调
     *
     * @param d Disposable
     */
    void doOnSubscribe(Disposable d);

    /**
     * 错误回调
     * @param errCode  错误码 0 = 系统错误 其它为服务端协定错误
     * @param errorMsg 错误信息
     */
    void doOnError(String errCode, String errorMsg);

    /**
     * 成功回调
     *
     * @param baseResult 基础泛型
     */
    void doOnNext(BaseResult<T> baseResult);

    /**
     * 请求完成回调
     */
    void doOnCompleted();
}
