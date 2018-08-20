package com.yidao.platform.discovery.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.IViewBottleActivity;
import com.yidao.platform.discovery.bean.PickBottleBean;
import com.yidao.platform.discovery.model.ThrowBottleObj;

import org.json.JSONException;
import org.json.JSONObject;

public class BottleActivityPresenter {
    private IViewBottleActivity mView;

    public BottleActivityPresenter(IViewBottleActivity mView) {
        this.mView = mView;
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
                .doOnSubscribe(disposable -> mView.pushAnim())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dismissSpaceShipWindow();
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            String errCode = (String) jsonObject.get("errCode");
                            MyLogger.e(errCode);
                            switch (errCode) {
                                case "1000":
                                    mView.throwSuccess();
                                    break;
                                case "1101":
                                    mView.countLimit((String)jsonObject.get("info"));
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
                .doOnSubscribe(disposable -> mView.pickAnim())
                .subscribe(new CommonObserver<PickBottleBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(PickBottleBean pickBottleBean) {
                        /**
                         * 	1000,捡到一个瓶子；1102，捡瓶子机会用完；1103，没有发现漂流瓶
                         */
                        switch (pickBottleBean.getErrCode()) {
                            case "1000":
                                MyLogger.e(pickBottleBean.getResult().toString());
                                break;
                            case "1102":
                            case "1103":
                                ToastUtils.showToast(pickBottleBean.getInfo());
                                break;
                        }
                    }
                });
    }
}
