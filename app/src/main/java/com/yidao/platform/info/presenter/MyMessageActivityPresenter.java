package com.yidao.platform.info.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.info.model.BottleMsgBean;
import com.yidao.platform.info.model.FindMsgBean;
import com.yidao.platform.info.view.IViewMyMessage;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyMessageActivityPresenter {
    private IViewMyMessage mView;

    public MyMessageActivityPresenter(IViewMyMessage mView) {
        this.mView = mView;
    }

    /**
     * 查询瓶子消息
     *
     * @param userId
     * @param mNextRequestPage
     * @param pageSize
     */
    public void qryBottleMess(String userId, int mNextRequestPage, int pageSize) {
        RxHttpUtils
                .createApi(ApiService.class)
                .qryBottleMess(userId, mNextRequestPage, pageSize)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<BottleMsgBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BottleMsgBean messageBean) {
                        switch (messageBean.getErrCode()) {
                            case "1000":
                                BottleMsgBean.ResultBean.PageBean pageBean = messageBean.getResult().getPage();
                                List<BottleMsgBean.ResultBean.ListBean> listBeans = messageBean.getResult().getList();
                                mView.successBottle(pageBean, listBeans);
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    /**
     * 查询评论消息
     *
     * @param userId
     * @param mNextRequestPage
     * @param pageSize
     */
    public void qryFindMess(String userId, int mNextRequestPage, int pageSize) {
        RxHttpUtils
                .createApi(ApiService.class)
                .qryFindMess(userId, mNextRequestPage, pageSize)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<FindMsgBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(FindMsgBean findMsgBean) {
                        switch (findMsgBean.getErrCode()) {
                            case "1000":
                                FindMsgBean.ResultBean.PageBean pageBean = findMsgBean.getResult().getPage();
                                List<FindMsgBean.ResultBean.ListBean> listBeans = findMsgBean.getResult().getList();
                                mView.successFind(pageBean, listBeans);
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    /**
     * 更新消息状态
     *
     * @param messageId
     */
    public void upMessageStat(String messageId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .upMessageStat(messageId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String data) {
                        mView.successUpdate();
                    }
                });
    }
}
