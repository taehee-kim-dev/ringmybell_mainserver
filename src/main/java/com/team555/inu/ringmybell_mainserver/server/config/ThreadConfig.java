package com.team555.inu.ringmybell_mainserver.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadConfig {
    @Bean
    public TaskExecutor thread1(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(800);
        threadPoolTaskExecutor.setThreadNamePrefix("socket_thread");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
