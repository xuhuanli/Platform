package com.yidao.platform.discovery.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.OssBean;
import com.yidao.platform.app.utils.OssUploadUtil;
import com.yidao.platform.discovery.model.SendFindObj;
import com.yidao.platform.discovery.view.DiscoveryEditorMessageInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditorMessagePresenter {

    private DiscoveryEditorMessageInterface mView;
    private OssUploadUtil ossUploadUtil;

    public EditorMessagePresenter(DiscoveryEditorMessageInterface view) {
        mView = view;
    }

    public void getOssInstance(Context context,String ossId, String ossSecret, String ossToken){
        ossUploadUtil = new OssUploadUtil(context,ossId, ossSecret, ossToken);
    }

    public void uploadFile(String filePath, @Nullable final Handler handler) {
        mView.showDialog();
        ossUploadUtil.uploadFile(filePath, handler);
    }

    public void uploadFile(int index,String filePath, @Nullable final Handler handler) {
        mView.showDialog();
        ossUploadUtil.uploadFile(index,filePath, handler);
    }

    public void sendMsg2Server(String userId, String content, @Nullable ArrayList<String> mUpLoadPicList) {
        SendFindObj sendFindObj = new SendFindObj();
        sendFindObj.setUserId(userId);
        sendFindObj.setContent(content);
        sendFindObj.setImgUrls(mUpLoadPicList);
        RxHttpUtils
                .createApi(ApiService.class)
                .sendFind(sendFindObj)
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
                            Boolean status = (Boolean) jsonObject.get("status");
                            if (status) {
                                mView.uploadSuccess();
                            }else {
                                mView.uploadFailed();
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

    /**
     * 获取认证(oss)
     */
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
}
