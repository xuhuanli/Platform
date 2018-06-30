package com.yidao.platform.info.view;

import com.yidao.platform.app.Util;

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
        Util.clearAppCache(cacheDir);
        Util.clearAppCache(externalCacheDir);
        onClearCacheFinished();
    }
}
