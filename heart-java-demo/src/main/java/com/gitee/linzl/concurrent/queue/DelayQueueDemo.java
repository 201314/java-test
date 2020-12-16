package com.gitee.linzl.concurrent.queue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueDemo implements Runnable {
    public static class Wangmin implements Delayed {
        private String name;
        // 身份证
        private String id;
        // 缴费
        private int money;
        // 截止时间
        private long endTime;

        public Wangmin(String name, String id, int money, long endTime) {
            this.name = name;
            this.id = id;
            this.money = money;
            this.endTime = endTime;
        }

        public String getEndTime() {
            Date date = new Date(endTime);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        }

        /**
         * 用来判断是否到了截止时间
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return endTime - System.currentTimeMillis();
        }

        /**
         * 相互比较排序用
         */
        @Override
        public int compareTo(Delayed delayed) {
            Wangmin w = (Wangmin) delayed;
            return this.getDelay(TimeUnit.MILLISECONDS) - w.getDelay(TimeUnit.MILLISECONDS) > 0 ? 1 : 0;
        }

        @Override
        public String toString() {
            return "网名:" + this.name + ",身份证:" + this.id + ",交钱" + this.money + "块";
        }
    }

    private DelayQueue<Wangmin> queue = new DelayQueue<>();

    public boolean yinye = true;

    public void shangji(String name, String id, int money) {
        Wangmin man = new Wangmin(name, id, money, 1000 * money + System.currentTimeMillis());
        this.queue.add(man);
        System.out.println("有新的网友上线啦:" + man);
    }

    @Override
    public void run() {
        while (yinye) {
            try {
                Wangmin man = queue.take();
                System.out.println("你可消费的时间已经用尽" + man.getEndTime() + ",请尽快充值:" + man);
                if (queue.isEmpty()) {
                    yinye = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("网吧开始营业");
        DelayQueueDemo siyu = new DelayQueueDemo();
        siyu.shangji("路人甲", "123", 1);
        siyu.shangji("路人乙", "234", 10);
        siyu.shangji("路人丙", "345", 5);

        Thread shangwang = new Thread(siyu);
        shangwang.start();
    }
}  