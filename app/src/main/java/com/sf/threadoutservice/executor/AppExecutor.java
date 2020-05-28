package com.sf.threadoutservice.executor;

import androidx.annotation.Nullable;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author by liyunlei
 * <p>
 * write on 2020/5/27
 * <p>
 * Class desc:
 */
public class AppExecutor {

    private static class Holder {
        private static AppExecutor instance = new AppExecutor();
    }

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAXIMUM_POOL_SIZE = CORE_POOL_SIZE + 4;
    private static final int KEEP_ALIVE_SECONDS = 3;

    private AppExecutor() {
    }

    public static AppExecutor getInstance() {
        return Holder.instance;
    }

    private final ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger count = new AtomicInteger(1);

        @Override
        public Thread newThread(@Nullable Runnable r) {
            return new Thread(r, "AppExecutor #" + count.getAndIncrement());
        }
    };

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(), threadFactory);

    public void submit(Runnable task) {
        threadPoolExecutor.submit(task);
    }

}
