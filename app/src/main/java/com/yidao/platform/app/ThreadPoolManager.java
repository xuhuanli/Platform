package com.yidao.platform.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {

    private final ExecutorService mService;

    private ThreadPoolManager() {
        int core = Runtime.getRuntime().availableProcessors();
        mService = Executors.newFixedThreadPool(core + 1);
    }

    public static ThreadPoolManager getInstance(){
        return ThreadPoolManagerHolder.threadPoolManager;
    }

    private static class ThreadPoolManagerHolder{
        private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();
    }

    public void addTask(Runnable r){
        mService.submit(r);
    }
}
