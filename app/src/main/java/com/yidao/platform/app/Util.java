package com.yidao.platform.app;

import java.io.File;

public class Util {

    /**
     * Gets app cache size,unit (M)
     *
     * @param file the file
     * @return the app cache size unit (M)
     */
    public static double getAppCacheSize(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getAppCacheSize(f);
                return size;
            } else {
                return (double) file.length() / 1024 / 1024;
            }
        }
        return 0;
    }

    /**
     * Delete files by directory.
     *
     * @param file the file or dir
     */
    public static void clearAppCache(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                for (File child : children) {
                    clearAppCache(child);
                }
            } else {
                file.delete();
            }
        }
    }
}
