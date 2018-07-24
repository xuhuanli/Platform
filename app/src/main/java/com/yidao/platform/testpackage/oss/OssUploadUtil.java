package com.yidao.platform.testpackage.oss;

import android.content.Context;
import android.util.Log;

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

public class OssUploadUtil {

    private static final String OSS_ID = "STS.NJShckQ3wT6zsw8XodyiDtkWu";
    private static final String OSS_Secret = "9zBG3yGyHQe4rNQPWdRSEK4KP6ki5CU4asiW8z3heTVa";
    private static final String OSS_Token = "CAISoQJ1q6Ft5B2yfSjIr4nmI9nfvOxW4/SRcVGJvG8xdeZom678lzz2IH5Oe3VtBe8WsvU/mm9Y7fYflqVoRoReREvCKMBt9YgPM4cToy6F6aKP9rUhpMCPFAr6UmzzvqL7Z+H+U6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj+wIDLkQRRLqL0AYZrFsKxBltdUROFbIKP+pKWSKuGfLC1dysQcO+wEP4K+kkMqH8Uic3h+o2+MNo43tLJ++ao4uHu8mD4fqhLItKfGfinABu0Mazsos0vwYowWgl8qGHlxc7y+BN+fp6dB1JGd7HPNkQfEY8aSjxaEp6rKMx9+nlgw+NOVUQjnZQ5u73MzHFeWmO9A0b7/nPG7X1dSCJn9lU+XshshxGoABfJXL16KTmepUNBya51crst/bltiishK13ntzGTKEpa/tjO1F2cHDJqRqTeYQjSbP9AZ1pKVRWYpPQdcj5DBXCAyllzznk2pkCiy1F/0rqJQW4iIH6V+Ci09ajfzhpgQNY39U3e77SLgjuz9q+Hc0qPokIqfsIRjy9l5UL8WnSRI=";

    String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    String stsServer = "STS应用服务器地址，例如http://abc.com";
    String bucketName = "ydplatform";
    private final OSSClient ossClient;

    public OssUploadUtil(Context context) {
        //初始化主要完成Endpoint设置、鉴权方式设置、Client参数设置
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSStsTokenCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(OSS_ID, OSS_Secret, OSS_Token);
        ossClient = new OSSClient(context, endpoint, credentialProvider, conf);
    }

    public void uploadFile(final String filePath) {
        PutObjectRequest put = new PutObjectRequest(bucketName, "test/IMG_" + FileUtil.formateTime(), filePath);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize + "文件目录 = " + filePath);
            }
        });
        OSSAsyncTask<PutObjectResult> task = ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常
                if (clientException != null) {
                    // 本地异常如网络异常等
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }
}
