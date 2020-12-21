package com.gitee.linzl.concurrent.executor;

import com.gitee.linzl.concurrent.callable.CallableDemo;
import com.gitee.linzl.concurrent.future.RecursiveTaskDemo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorService , Executor
 * <p>
 * ForkJoinWorkerThreadFactory
 * ManagedBlocker
 *
 * @author linzhenlie-jk
 * @date 2020/12/14
 */
public class ForkJoinPoolDemo {
    public static void submit1() {
        System.out.println("=========submit1=============");
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> future = pool.submit(new RecursiveTaskDemo(10));
        System.out.println("结果:" + future.join());
        pool.shutdown();
    }

    public static void submit2() {
        System.out.println("=========submit2=============");
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<String> future = pool.submit(new CallableDemo());
        System.out.println("结果:" + future.join());
        pool.shutdown();
    }

    public static void submit3() {
        System.out.println("=========submit3=============");
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<String> future = pool.submit(() -> {
            System.out.println("Runnable任务执行");
        }, "返回指定结果");
        System.out.println("结果:" + future.join());
        pool.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        submit1();
        TimeUnit.SECONDS.sleep(10);
        submit2();
        TimeUnit.SECONDS.sleep(10);
        submit3();
    }
}
