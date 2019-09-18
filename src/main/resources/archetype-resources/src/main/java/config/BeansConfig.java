package ${package}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Bean配置
 */
@Configuration
@EnableAsync
public class BeansConfig {
    /**
     * 设置最大线程池的数量为CPU核心线程数的两倍，保证最优的效率，目前的应用都不是CPU密集型，适当调整大小
     */
    private final static Integer MAX_FIXED_THREADS = Runtime.getRuntime().availableProcessors() * 6;

    /**
     * 全局线程池配置
     * @return 线程池
     */
    @Bean
    public AsyncTaskExecutor asyncTaskExecutor() {
        LumiThreadPoolTaskExecutor executor = new LumiThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(10);
        // 设置最大线程数
        executor.setMaxPoolSize(MAX_FIXED_THREADS < 10 ? 20 : MAX_FIXED_THREADS);
        // 设置队列容量
        executor.setQueueCapacity(200);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(3600);
        // 设置默认线程名称
        executor.setThreadNamePrefix("core-thread-");
        // 设置拒绝策略（CallerRunsPolicy：用于被拒绝任务的处理程序，它直接在execute方法的调用线程中运行被拒绝的任务）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    public static void main(String[] s) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
