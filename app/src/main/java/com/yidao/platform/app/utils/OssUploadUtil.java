package com.yidao.platform.app.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.yidao.platform.info.model.EventTouXiangInfo;

import org.greenrobot.eventbus.EventBus;

public class OssUploadUtil {

    private final OSSClient ossClient;

    public OssUploadUtil(Context context, String ossId, String ossSecret, String ossToken) {
        //初始化主要完成Endpoint设置、鉴权方式设置、Client参数设置
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSStsTokenCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(ossId, ossSecret, ossToken);
        ossClient = new OSSClient(context, Constant.OSS_ENDPOINT, credentialProvider, conf);
    }

    public void uploadFile(final String filePath, @Nullable final Handler handler) {
        String objectKey = "Find/IMG_" + FileUtil.formateTime();
        PutObjectRequest put = new PutObjectRequest(Constant.OSS_BUCKET_NAME, objectKey, filePath);
        // 异步上传时可以设置进度回调
        /*put.setProgressCallback((request, currentSize, totalSize) -> MyLogger.d("currentSize: " + currentSize + " totalSize: " + totalSize + "文件目录 = " + filePath));*/
        OSSAsyncTask<PutObjectResult> task = ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                String pathOnOss = "https://ydplatform.oss-cn-hangzhou.aliyuncs.com/" + objectKey;
                if (handler != null) { //import : 计数oss成功counter handler一般是给发布朋友圈多图
                    Message msg = Message.obtain();
                    msg.what = 0;
                    msg.obj = pathOnOss;
                    handler.sendMessage(msg);
                } else {  //发布一个String的event
                    EventTouXiangInfo info = new EventTouXiangInfo(true, pathOnOss);
                    EventBus.getDefault().post(info);
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常
                if (handler != null) { //import : 计数oss成功counter handler一般是给发布朋友圈多图
                    handler.sendEmptyMessage(1);
                } else {  //发布一个String的event
                    EventTouXiangInfo info = new EventTouXiangInfo(false, "");
                    EventBus.getDefault().post(info);
                }
            }
        });
    }

    public void uploadFile(int index,final String filePath, @Nullable final Handler handler) {
        String objectKey = "Find/IMG_" + FileUtil.formateTime();
        PutObjectRequest put = new PutObjectRequest(Constant.OSS_BUCKET_NAME, objectKey, filePath);
        OSSAsyncTask<PutObjectResult> task = ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                String pathOnOss = "https://ydplatform.oss-cn-hangzhou.aliyuncs.com/" + objectKey;
                if (handler != null) { //import : 计数oss成功counter handler一般是给发布朋友圈多图
                    Message msg = Message.obtain();
                    msg.what = 0;
                    Bundle bundle = new Bundle();
                    bundle.putInt("index",index);
                    bundle.putString("pathOnOss",pathOnOss);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } else {  //发布一个String的event
                    EventTouXiangInfo info = new EventTouXiangInfo(true, pathOnOss);
                    EventBus.getDefault().post(info);
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常
                if (handler != null) { //import : 计数oss成功counter handler一般是给发布朋友圈多图
                    handler.sendEmptyMessage(1);
                } else {  //发布一个String的event
                    EventTouXiangInfo info = new EventTouXiangInfo(false, "");
                    EventBus.getDefault().post(info);
                }
            }
        });
    }
}
