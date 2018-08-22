package com.yidao.platform.discovery.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;

public class MyBottlePresenter {
    private IViewMyBottleActivity mView;

    public MyBottlePresenter(IViewMyBottleActivity mView) {
        this.mView = mView;
    }

    /**
     * 查询我的瓶子列表
     * @param userId
     */
    public void qryBottleList(String userId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .qryBottleList(userId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.errorNet();
                    }

                    @Override
                    protected void onSuccess(String data) {
                        MyLogger.e(data);
                        mView.loadMyBottleList();
                    }
                });
    }
}
