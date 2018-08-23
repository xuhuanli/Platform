package com.yidao.platform.discovery.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.bean.BottleDtlBean;
import com.yidao.platform.discovery.model.ReplyBottleListObj;
import com.yidao.platform.discovery.model.ReplyBottleObj;
import com.yidao.platform.discovery.view.DiscoveryBottleDetailInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class DiscoveryBottleDetailPresenter {
    private DiscoveryBottleDetailInterface mView;

    public DiscoveryBottleDetailPresenter(DiscoveryBottleDetailInterface view) {
        this.mView = view;
    }

    /**
     * 回复瓶子
     *
     * @param obj
     */
    public void replyBottle(ReplyBottleObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .replyBottle(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            String errCode = jsonObject.getString("errCode");
                            switch (errCode) {
                                case "1000":
                                    mView.commentSuccess();
                                    break;
                                default:
                                    mView.showErrorInfo(jsonObject.getString("info"));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 查询瓶子详情
     *
     * @param bottleId
     * @param sessionId
     */
    public void qryBottleDtl(String bottleId, String sessionId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .qryBottleDtl(bottleId, sessionId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BottleDtlBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(BottleDtlBean bottleDtlBean) {
                        switch (bottleDtlBean.getErrCode()) {
                            case "1000":
                                mView.showBottleDtl(bottleDtlBean.getResult());
                                break;
                        }
                    }
                });
    }

    /**
     * 瓶子列表的回复
     * @param obj
     */
    public void replyBottleList(ReplyBottleListObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .replyBottleList(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            String errCode = jsonObject.getString("errCode");
                            switch (errCode) {
                                case "1000":
                                    mView.commentSuccess();
                                    break;
                                default:
                                    mView.showErrorInfo(jsonObject.getString("info"));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
