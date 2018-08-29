package com.yidao.platform.info.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.OssBean;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.info.model.UserInfoBean;
import com.yidao.platform.info.view.IViewPersonInfomationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PersonInfomationActivityPresenter {
    private IViewPersonInfomationActivity mView;

    public PersonInfomationActivityPresenter(IViewPersonInfomationActivity mView) {
        this.mView = mView;
    }

    public <T> void updateUserInfo(String userId, String key, T value) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", userId);
        map.put(key, String.valueOf(value));
        RxHttpUtils
                .createApi(ApiService.class)
                .updateUserInfo(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            boolean status = (boolean) jsonObject.get("status");
                            MyLogger.e(status ? "修改成功" : "修改失败");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void getOssAccess() {
        RxHttpUtils
                .createApi(ApiService.class)
                .getUploadMsg()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<OssBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(OssBean ossBean) {
                        if (ossBean.isStatus()) {
                            mView.saveOss(ossBean.getResult());
                        }
                    }
                });
    }

    /**
     * 查询用户
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

