package com.xuhuanli.httplibrary.download;

public interface ProgressListener {

    void onResponseProgress(long bytesRead, long contentLength, int progress, boolean done, String filePath);


}
