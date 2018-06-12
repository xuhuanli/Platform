package com.xuhuanli.httplibrary.download;


import com.xuhuanli.httplibrary.MyRetrofit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class DownloadManager {


    public File saveFile(ResponseBody response, final String destFileName, ProgressListener progressListener) throws IOException {

        String destFileDir = MyRetrofit.getContext().getExternalFilesDir(null) + File.separator;

        long contentLength = response.contentLength();
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.byteStream();

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);

                final long finalSum = sum;

                progressListener.onResponseProgress(finalSum, contentLength, (int) ((finalSum * 1.0f / contentLength) * 100), finalSum == contentLength, file.getAbsolutePath());
            }
            fos.flush();

            return file;

        } finally {
            try {
                response.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }

        }
    }


}
