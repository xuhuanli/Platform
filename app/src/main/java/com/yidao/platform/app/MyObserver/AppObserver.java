package com.yidao.platform.app.MyObserver;

import io.reactivex.disposables.Disposable;

public abstract class AppObserver<T> extends BaseAppObserver<T> {

    /**
     * 失败回调
     *
     * @param errCode  错误码 "0"表示http错误
     * @param errorMsg 错误信息
     */
    protected abstract void onError(String errCode, String errorMsg);

    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(T data);

    @Override
    public void doOnSubscribe(Disposable d) {
        //不关注这个方法
    }

    @Override
    public void doOnError(String errCode, String errorMsg) {
        onError(HTTP_ERROR, errorMsg);
    }

    @Override
    public void doOnNext(BaseResult<T> baseResult) {
        //在这里需要跟服务端协定各种错误码,然后做相应回调处理
        T result = baseResult.getResult();
        switch (baseResult.getErrCode()) {
            case "1000":
                //成功获取数据
                onSuccess(result);
                break;
            //这里没有对code做任何处理，只是往下抛出，如果统一，可以在这里处理掉
            default:
                onError(baseResult.getErrCode(), baseResult.getInfo());
                break;
        }
    }

    @Override
    public void doOnCompleted() {

    }
}
