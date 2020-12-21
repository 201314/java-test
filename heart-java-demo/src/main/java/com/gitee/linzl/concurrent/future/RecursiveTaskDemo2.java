package com.gitee.linzl.concurrent.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 网站会员大约有7万个，要给这7万个用户发送营销短信。而短信运营商那边要求每次发送的手机号码最好要少于3000。
 * <p>
 * 递归分治算法时
 *
 * @author linzl
 * @description
 * @email 2225010489@qq.com
 * @date 2018年5月21日
 */
public class RecursiveTaskDemo2 extends RecursiveTask<Integer> {
    private static final long serialVersionUID = 9129602819316319630L;
    final int start;
    final int end;
    final List<Integer> list;

    public RecursiveTaskDemo2(int start, int end, List<Integer> list) {
        this.start = start;
        this.end = end;
        this.list = list;
    }

    protected Integer compute() {
        // 每次任务最多处理3000条短信，为了方便改为30
        if (end - start <= 30) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + "发送短信给=>" + i);
            }
            return 1;
        }
        // 将大任务分解成两个小任务
        int middle = (start + end) / 2;
        RecursiveTaskDemo2 f1 = new RecursiveTaskDemo2(start, middle, list);
        RecursiveTaskDemo2 f2 = new RecursiveTaskDemo2(middle, end, list);
        // 并行执行两个小任务
        f1.fork();
        f2.fork();
        return f1.join() + f2.join();
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        List<Integer> list = new ArrayList<>();
        for (int index = 0; index < 1000; index++) {
            list.add(index);
        }
        ForkJoinTask<Integer> future = pool.submit(new RecursiveTaskDemo2(0, list.size(), list));
        System.out.println("结果:" + future.join());
        pool.shutdown();
    }
}
