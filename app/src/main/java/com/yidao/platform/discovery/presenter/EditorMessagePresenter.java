package com.yidao.platform.discovery.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.OssUploadUtil;
import com.yidao.platform.discovery.model.SendFindObj;
import com.yidao.platform.discovery.view.DiscoveryEditorMessageInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditorMessagePresenter {

    private DiscoveryEditorMessageInterface mView;
    private final OssUploadUtil ossUploadUtil;

    public EditorMessagePresenter(DiscoveryEditorMessageInterface view, Context context) {
        mView = view;
        ossUploadUtil = new OssUploadUtil(context);
    }

    public void uploadFile(String filePath, @Nullable final Handler handler) {
        mView.showDialog();
        ossUploadUtil.uploadFile(filePath, handler);
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
