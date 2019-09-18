package ${package}.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程异常日志打印
 */
@Slf4j
public class LumiThreadPoolExecutor extends ThreadPoolExecutor {

    public LumiThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * asyncTaskExecutor.execute 方式抛出的异常都会在这里记录
     * @param r
     * @param t
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        if (null != t) {
            log.error("thread run error", t);
        }
    }
}