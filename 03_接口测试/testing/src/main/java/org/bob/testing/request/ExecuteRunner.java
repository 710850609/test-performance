package org.bob.testing.request;

import org.bob.testing.util.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class ExecuteRunner {

    private List<HttpExecutor> executors = new ArrayList<>();

    private boolean stopForError = false;

    private Integer threadCycleNum = 1;

    private Integer threadNum = 1;

    private ExecuteRunner() {}

    private CountDownLatch countDownLatch;

    public static ExecuteRunner build() {
        return new ExecuteRunner();
    }

    /**
     * 添加多个请求
     * @param configs
     * @return
     */
    public ExecuteRunner addRequests(List<RequestBuilder> configs) {
        configs.forEach(config -> {
            this.addRequest(config);
        });
        return this;
    }

    /**
     * 添加单个请求
     * @param config
     * @return
     */
    public ExecuteRunner addRequest(RequestBuilder config) {
        this.executors.add(HttpExecutor.build(config));
        return this;
    }

    /**
     * 是否出现异常停止
     * @param stopForError
     * @return
     */
    public ExecuteRunner stopForError(boolean stopForError) {
        this.stopForError = stopForError;
        return this;
    }

    /**
     * 单个线程循环次数
     * @param threadCycleNum
     * @return
     */
    public ExecuteRunner threadCycleNum(int threadCycleNum) {
        this.threadCycleNum = threadCycleNum;
        return this;
    }

    /**
     * 启动线程个数
     * @param threadNum
     * @return
     */
    public ExecuteRunner threadNum(int threadNum) {
        this.threadNum = threadNum;
        return this;
    }

    public void start() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(threadNum);
        CountDownLatch countDownLatch = new CountDownLatch(threadNum * threadCycleNum * executors.size());
        int threadCount = threadNum;
        while (threadCount-- > 0) {
            threadPool.addTask(() -> {
                int count = threadCycleNum;
                while (count-- >= 0) {
                    executors.forEach(executor -> {
                        try {
                            executor.setCountDownLatch(countDownLatch);
                            executor.run();
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (!this.stopForError) {
                                throw e;
                            }
                        }
                    });
                }
            });
        }
        countDownLatch.await();
        System.exit(0);
    }
}