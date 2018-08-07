package com.yidao.platform.discovery.presenter;

import android.content.Context;
import android.os.Handler;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.utils.FileUtil;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.DiscoveryEditorMessageActivity;
import com.yidao.platform.discovery.view.DiscoveryEditorMessageInterface;

public class EditorMessagePresenter {

    private DiscoveryEditorMessageInterface view;

    private final OSSClient ossClient;

    public EditorMessagePresenter(DiscoveryEditorMessageInterface view) {
        this.view = view;
        Context mContext = (DiscoveryEditorMessageActivity) view;
        //初始化主要完成Endpoint设置、鉴权方式设置、Client参数设置
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSStsTokenCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(Constant.OSS_ID, Constant.OSS_SECRET, Constant.OSS_TOKEN);
        ossClient = new OSSClient(mContext, Constant.OSS_ENDPOINT, credentialProvider, conf);
    }

    public void uploadFile(final String filePath, final Handler handler) {
        PutObjectRequest put = new PutObjectRequest(Constant.OSS_BUCKET_NAME, "test/IMG_" + FileUtil.formateTime(), filePath);
        // 异步上传时可以设置进度回调
        /*put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                MyLogger.d("currentSize: " + currentSize + " totalSize: " + totalSize + "文件目录 = " + filePath);
            }
        });*/
        OSSAsyncTask<PutObjectResult> task = ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                //import : 计数oss成功counter
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常
                view.uploadPicFailed();
            }
        });
    }
}
