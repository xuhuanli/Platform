package com.yidao.platform.service.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.Constant;
import com.yidao.platform.service.IViewServiceFragment;
import com.yidao.platform.service.model.BpObj;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceFragmentPresenter {
    private IViewServiceFragment mView;

    public ServiceFragmentPresenter(IViewServiceFragment mView) {
        this.mView = mView;
    }

    /**
     * BP 申请
     *
     * @param bpObj
     */
    public void sendBpApply(BpObj bpObj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .bpApply(bpObj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            switch (jsonObject.getString(Constant.STRING_ERRCODE)) {
                                case "1000":
                                    ToastUtils.showToast("申请成功");
                                    mView.applySuccess();
                                    break;
                                default:
                                    ToastUtils.showToast("申请失败,请稍后再试");
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
