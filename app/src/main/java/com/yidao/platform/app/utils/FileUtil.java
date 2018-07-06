package com.yidao.platform.app.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtil {

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

    /**
     * Gets disk cache dir.if sdcard existed , return getExternalCacheDir directory
     * else return getCacheDir directory.
     *
     * @param context    the context
     * @param uniqueName the unique name
     * @return the disk cache dir
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
