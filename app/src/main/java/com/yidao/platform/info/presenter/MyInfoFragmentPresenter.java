package com.yidao.platform.info.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.info.model.MineInfoBean;
import com.yidao.platform.info.model.UserInfoBean;
import com.yidao.platform.info.view.IViewMineInfo;

public class MyInfoFragmentPresenter {
    private IViewMineInfo mView;

    public MyInfoFragmentPresenter(IViewMineInfo mView) {
        this.mView = mView;
    }

    /**
     * 获取'我的'页面参数
     *
     * @param userId
     */
    public void getMineInfo(String userId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .getMineInfo(userId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<MineInfoBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(MineInfoBean data) {
                        switch (data.getErrCode()) {
                            case "1000":
                                mView.success(data);
                                break;
                        }
                    }
                });
    }

    /**
     * 按id查询用户明细
     *
     * @param userId
     */
    public void qryUserById(String userId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .qryUserById(userId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<UserInfoBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(UserInfoBean userInfoBean) {
                        switch (userInfoBean.getErrCode()) {
                            case "200":
                                mView.successInfo(userInfoBean.getResult());
                                break;
                            default:
                                mView.showError(userInfoBean.getInfo());
                                break;
                        }
                    }
                });
    }
}
