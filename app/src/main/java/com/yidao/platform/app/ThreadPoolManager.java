package com.yidao.platform.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 好像没什么b用 维护2个线程得了
 */
public class ThreadPoolManager {

    private final ExecutorService mService;

    private ThreadPoolManager() {
        mService = Executors.newFixedThreadPool(2);
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
