package org.bob.testing.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */
public class ThreadPool {

    int size = 1;
    ThreadPoolExecutor threadPoolExecutor;

    public ThreadPool(int size) {
        this.size = size;
        int corePoolSize = size;
        int maximumPoolSize = size;
        long keepAliveTime = 60L;
        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    /**
     * 添加线程任务
     * @param command
     */
    public void addTask(Runnable command) {
        this.threadPoolExecutor.execute(command);
    }

}
