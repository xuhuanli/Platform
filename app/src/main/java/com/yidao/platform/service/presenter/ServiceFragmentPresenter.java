package com.yidao.platform.service.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
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
     * @param bpObj
     */
    public void sendBpApply(BpObj bpObj) {
        RxHttpUtils.getSInstance()
                .baseUrl("http://10.10.20.27:8080/")
                .createSApi(ApiService.class)
                .bpApply(bpObj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError();
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            int code = (int) jsonObject.get("code");
                            if (code == 200) {
                                ToastUtils.showToast("申请成功");
                                mView.applySuccess();
                            } else {
                                ToastUtils.showToast((String) jsonObject.get("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void showError() {
        ToastUtils.showToast("网络连接失败，请查看网络");
    }
}
