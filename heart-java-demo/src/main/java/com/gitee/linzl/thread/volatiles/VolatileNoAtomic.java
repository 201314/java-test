package com.gitee.linzl.thread.volatiles;

/**
 * volatile关键字不具备synchronized关键字的原子性（同步）
 * 可使用 AtomicInteger 达到同样效果且同步
 */
public class VolatileNoAtomic extends Thread {
    private static volatile int count;

    @Override
    public void run() {
        addCount();
    }

    private static void addCount() {
        for (int i = 0; i < 1000; i++) {
            count++;
        }
        System.out.println(count);
    }

    public static void main(String[] args) {
        VolatileNoAtomic[] arr = new VolatileNoAtomic[10];
        for (int i = 0; i < 10; i++) {
            arr[i] = new VolatileNoAtomic();
        }

        for (int i = 0; i < 10; i++) {
            arr[i].start();
        }
    }
}
