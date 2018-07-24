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
import com.yidao.platform.app.utils.FileUtil;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.DiscoveryEditorMessageActivity;
import com.yidao.platform.discovery.view.DiscoveryEditorMessageInterface;

public class EditorMessagePresenter {

    private DiscoveryEditorMessageInterface view;
    private Context mContext;
    private static final String OSS_ID = "STS.NJJEqyqqfUCHEpRUjQviQQmhp";
    private static final String OSS_Secret = "9SZrAMjz5xvUGwuApq7EEr1Y8zBhNnTiE8wAQpVSb1dm";
    private static final String OSS_Token = "CAISoQJ1q6Ft5B2yfSjIr4n/DsvNnK5H4oGjR1bjsWoEeuZ9vqjDkjz2IH5Oe3VtBe8WsvU/mm9Y7fYflqVoRoReREvCKMBt9YgPVv5Pvy6F6aKP9rUhpMCPFAr6UmzzvqL7Z+H+U6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj+wIDLkQRRLqL0AYZrFsKxBltdUROFbIKP+pKWSKuGfLC1dysQcO+wEP4K+kkMqH8Uic3h+o2+MNo43tLJ++ao4uHu8mD4fqhLItKfGfinABu0Mazsos0vwYowWgl8qGHlxc7y+BN+fp6dB1JGd7HPNkQfEY8aSjxaEp6rKMx9+nlgw+NOVUQjnZQ5u73MzHFeWmO9A0b7/nPG7X1dSCJn9lU+XshshxGoABl3HnN2gn36zyRcKz5mPccTkTwl/vwxGh7vGOq7n6eyt0E9QUE2uX5yctXmUctVqGOZHxVsQVbt0wZWX3Dt2hrSaeLG9zLvrcjxzaztvEzj7VJ4752zM3J8rgeVckrd0N9O1/kWwmR4qgLw4ZJMSS9LU2qZWqnew4HisdImfnLCY=";

    String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    String stsServer = "STS应用服务器地址，例如http://abc.com";
    String bucketName = "ydplatform";
    private final OSSClient ossClient;

    public EditorMessagePresenter(DiscoveryEditorMessageInterface view) {
        this.view = view;
        mContext = (DiscoveryEditorMessageActivity) view;
        //初始化主要完成Endpoint设置、鉴权方式设置、Client参数设置
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSStsTokenCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(OSS_ID, OSS_Secret, OSS_Token);
        ossClient = new OSSClient(mContext, endpoint, credentialProvider, conf);
    }

    public void uploadFile(final String filePath, final Handler handler) {
        PutObjectRequest put = new PutObjectRequest(bucketName, "test/IMG_" + FileUtil.formateTime(), filePath);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                MyLogger.d("currentSize: " + currentSize + " totalSize: " + totalSize + "文件目录 = " + filePath);
            }
        });
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
