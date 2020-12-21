package com.gitee.linzl.concurrent.executor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ScheduledThreadPoolExecutorDemo {
    public static void testDelayQueue() {
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

    public static void scheduleWithFixedDelay() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        System.out.println("延迟队列线程池");
        for (int i = 0; i < 2; i++) {
            final int index = i;
            executor.scheduleWithFixedDelay(() -> {
                System.out.println("延迟顺序执行:" + index + ",时间：" + format.format(LocalDateTime.now()));
            }, 5, 10, TimeUnit.SECONDS);
        }
        System.out.println("end main时间:" + format.format(LocalDateTime.now()));
    }

    public static void main(String[] args) throws InterruptedException {
        testDelayQueue();
        TimeUnit.SECONDS.sleep(10);
        scheduleWithFixedDelay();
    }
}