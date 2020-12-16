package com.gitee.linzl.concurrent.executor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ScheduledThreadPoolExecutorDemo {
    public void testDelayQueue() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        System.out.println("延迟队列线程池");
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executor.schedule(() -> {
                System.out.println("延迟顺序执行:" + index + ",时间：" + format.format(LocalDateTime.now()));
            }, 10, TimeUnit.SECONDS);
        }
        System.out.println("end main时间:" + format.format(LocalDateTime.now()));
    }

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(() -> System.out.println("run"), 5, 1, TimeUnit.SECONDS);
    }
}