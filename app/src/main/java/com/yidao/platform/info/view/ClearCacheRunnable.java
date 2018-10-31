package com.yidao.platform.info.view;

import android.os.Environment;

import com.yidao.platform.app.utils.FileUtil;

import java.io.File;

abstract class ClearCacheRunnable implements Runnable {
    private File cacheDir;
    private File externalCacheDir;

    /**
     * Instantiates a new Clear cache runnable.
     *
     * @param cacheDir         the cache dir
     * @param externalCacheDir the external cache dir
     */
    ClearCacheRunnable(File cacheDir, File externalCacheDir) {
        this.cacheDir = cacheDir;
        this.externalCacheDir = externalCacheDir;
    }

    abstract void onClearCacheFinished();

    abstract void onClearCacheStarted();

    @Override
    public void run() {
        onClearCacheStarted();
        FileUtil.clearAppCache(cacheDir);
        FileUtil.clearAppCache(externalCacheDir);
        FileUtil.clearAppCache(new File(Environment.getExternalStorageDirectory()+"/yidao"));
        onClearCacheFinished();
    }
}
