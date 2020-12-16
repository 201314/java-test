package com.gitee.linzl.concurrent.executor;

import java.util.Vector;
import java.util.concurrent.*;

/**
 * ExecutorService , Executor
 * <p>
 * <p>
 * 比较简单的多线程可以直接使用Executor，但是强烈建议使用ThreadPoolExecutor，这样使用者可以非常方便的知道线程池信息
 *
 * @author linzhenlie
 * @date 2020/12/14
 */
public class ThreadPoolExecutorDemo {
    public void testArrayBlockingQueue() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5));
        System.out.println("有界队列:ArrayBlockingQueue");
        for (int i = 0; i < 20; i++) {
            final int index = i;
            executor.submit(() -> {
                System.out.println("乱序执行:" + index);
            });
        }
    }

    public void testLinkedBlockingDeque() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        System.out.println("双向队列:LinkedBlockingDeque");
        System.out.println("是一种具有队列和栈的性质的数据结构");
        for (int i = 0; i < 20; i++) {
            final int index = i;
            executor.submit(() -> {
                System.out.println("顺序执行:" + index);
            });
        }
    }


    public void testLinkedBlockingQueue() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        System.out.println("无界队列:LinkedBlockingQueue");
        for (int i = 0; i < 20; i++) {
            final int index = i;
            executor.submit(() -> {
                System.out.println("顺序执行:" + index);
            });
        }
    }


    public void testLinkedTransferQueue() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 60, TimeUnit.SECONDS,
                new LinkedTransferQueue<>());
        System.out.println("无界队列:LinkedTransferQueue");
        for (int i = 0; i < 20; i++) {
            final int index = i;
            executor.submit(() -> {
                System.out.println("顺序执行:" + index);
            });
        }
    }


    public void test() throws InterruptedException {
        final Vector<Integer> vector = new Vector<>();

        // corePoolSize,maximumPoolSize,keepAliveTime,TimeUnit,BlockingQueue
        ThreadPoolExecutor tp = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10));
        System.out.println("初始PoolSize：" + tp.getPoolSize());

        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 20; i++) {
            tp.execute(() -> {
                vector.add(random.nextInt());
            });
        }

        TimeUnit.SECONDS.sleep(1L);
        tp.shutdown();
        System.out.println("PoolSize：" + tp.getPoolSize());
        System.out.println("已完成的任务：" + tp.getCompletedTaskCount());
        System.out.println("活动的线程数：" + tp.getActiveCount());
        System.out.println("list大小：" + vector.size());
    }
}
