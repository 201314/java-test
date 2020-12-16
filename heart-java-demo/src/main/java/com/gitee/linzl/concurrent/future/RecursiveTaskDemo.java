package com.gitee.linzl.concurrent.future;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * RunnableFuture、Future、Runnable
 */
public class RecursiveTaskDemo extends RecursiveTask<Integer> {
    private static final long serialVersionUID = 9129602819316319630L;
    private final int n;

    public RecursiveTaskDemo(int n) {
        this.n = n;
    }

    // 斐波那契数列
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }
        System.err.println("=====任务分解======");
        RecursiveTaskDemo f1 = new RecursiveTaskDemo(n - 1);
        RecursiveTaskDemo f2 = new RecursiveTaskDemo(n - 2);
        f1.fork();
        f2.fork();
        return f1.join() + f2.join();
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> future = pool.submit(new RecursiveTaskDemo(10));
        System.out.println("结果:" + future.join());
        pool.shutdown();
    }
}
