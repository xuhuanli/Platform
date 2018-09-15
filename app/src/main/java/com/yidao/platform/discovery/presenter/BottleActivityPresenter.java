package com.yidao.platform.discovery.presenter;

import android.widget.ImageView;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.google.gson.Gson;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.bean.PickBottleBean;
import com.yidao.platform.discovery.model.ThrowBottleObj;
import com.yidao.platform.discovery.view.IViewBottleActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class BottleActivityPresenter {
    private IViewBottleActivity mView;

    public BottleActivityPresenter(IViewBottleActivity mView) {
        this.mView = mView;
    }

    /**
     * 验证扔瓶子次数
     *
     * @param userId
     */
    public void validThrowTimes(String userId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .validThrowTimes(userId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            String errCode = (String) jsonObject.get(Constant.STRING_ERRCODE);
                            MyLogger.e(errCode);
                            switch (errCode) {
                                case "1000":
                                    mView.grantThrowValid();
                                    break;
                                default:
                                    mView.throwLimited((String) jsonObject.get("info"));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 扔瓶子
     *
     * @param obj
     */
    public void throwBottle(ThrowBottleObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .throwBottle(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dismissSpaceShipWindow();
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            String errCode = (String) jsonObject.get(Constant.STRING_ERRCODE);
                            MyLogger.e(errCode);
                            switch (errCode) {
                                case "1000":
                                    mView.throwSuccess();
                                    break;
                                case "1101":
                                    mView.throwLimited((String) jsonObject.get("info"));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 验证扔瓶子次数
     *
     * @param userId
     * @param mIvPullBottle
     */
    public void validPickTimes(String userId, ImageView mIvPullBottle) {
        RxHttpUtils
                .createApi(ApiService.class)
                .validPickTimes(userId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mIvPullBottle.setEnabled(true);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        mIvPullBottle.setEnabled(true);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            String errCode = (String) jsonObject.get(Constant.STRING_ERRCODE);
                            MyLogger.e(errCode);
                            switch (errCode) {
                                case "1000":
                                    mView.grantPickValid();
                                    break;
                                default:
                                    mView.countLimit((String) jsonObject.get("info"));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 捡瓶子
     *
     * @param userId
     */
    public void pickBottle(String userId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .pickBottle(userId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.netError();
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            String errCode = (String) jsonObject.get(Constant.STRING_ERRCODE);
                            switch (errCode) {
                                //1000,捡到一个瓶子；1102，捡瓶子机会用完；1103，没有发现漂流瓶
                                case "1000":
                                    String result = jsonObject.getString("result");
                                    PickBottleBean.ResultBean resultBean = new Gson().fromJson(result, PickBottleBean.ResultBean.class);
                                    mView.getOneBottle(resultBean);
                                    break;
                                case "1102":
                                    mView.countLimit(jsonObject.getString("info"));
                                    break;
                                case "1103":
                                    mView.errorStatus(jsonObject.getString("info"));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
