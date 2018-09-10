package com.yidao.platform.read.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.info.model.BlackListBean;
import com.yidao.platform.info.view.IViewBlackListActivity;

import java.util.List;

public class BlackListActivityPresenter {
    private IViewBlackListActivity mView;

    public BlackListActivityPresenter(IViewBlackListActivity mView) {
        this.mView = mView;
    }

    /**
     * 查询黑名单list
     *
     * @param mNextRequestPage
     * @param pageSize
     * @param userId
     */
    public void qryShieldUsers(int mNextRequestPage, int pageSize, String userId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .qryShieldUsers(mNextRequestPage, pageSize, userId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BlackListBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BlackListBean blackListBean) {
                        switch (blackListBean.getErrCode()) {
                            case "1000":
                                BlackListBean.ResultBean result = blackListBean.getResult();
                                if (result != null) {
                                    if (result.getList().size() < Constant.PAGE_SIZE) {
                                        mView.loadMoreEnd(true);
                                    } else {
                                        mView.loadMoreComplete();
                                    }
                                    List<BlackListBean.ResultBean.ListBean> dataList = result.getList();
                                    if (result.getPage().getPageIndex() == 0||result.getPage().getPageIndex() == 1) { //page = 1时，表示初始列表值
                                        mView.loadRecyclerData(dataList);
                                    } else { //上拉
                                        mView.loadMoreData(dataList);
                                    }
                                }
                                break;
                            default:
                                mView.noBlackUser();
                                break;
                        }
                    }
                });
    }

    /**
     * 解禁
     * @param shUserId
     * @param userId
     */
    public void cancelShieldUser(String shUserId, String userId,BlackListBean.ResultBean.ListBean item) {
        RxHttpUtils
                .createApi(ApiService.class)
                .cancelShieldUser(shUserId, userId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String s) {
                        mView.cancelSuccess(item);
                    }
                });
    }
}
