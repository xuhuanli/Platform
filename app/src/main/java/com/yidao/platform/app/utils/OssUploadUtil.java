package com.yidao.platform.app.utils;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.yidao.platform.app.Constant;

import org.greenrobot.eventbus.EventBus;

public class OssUploadUtil {

    private final OSSClient ossClient;

    public OssUploadUtil(Context context) {
        //初始化主要完成Endpoint设置、鉴权方式设置、Client参数设置
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSStsTokenCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(Constant.OSS_ID, Constant.OSS_SECRET, Constant.OSS_TOKEN);
        ossClient = new OSSClient(context, Constant.OSS_ENDPOINT, credentialProvider, conf);
    }

    public void uploadFile(final String filePath, @Nullable final Handler handler) {
        PutObjectRequest put = new PutObjectRequest(Constant.OSS_BUCKET_NAME, "test/IMG_" + FileUtil.formateTime(), filePath);
        // 异步上传时可以设置进度回调
        /*put.setProgressCallback((request, currentSize, totalSize) -> MyLogger.d("currentSize: " + currentSize + " totalSize: " + totalSize + "文件目录 = " + filePath));*/
        OSSAsyncTask<PutObjectResult> task = ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                //import : 计数oss成功counter
                if (handler != null) {
                    handler.sendEmptyMessage(0);
                } else {
                    String json = "success";
                    EventBus.getDefault().post(json);
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常
                String json = "fail";
                EventBus.getDefault().post(json);
            }
        });
    }
}
