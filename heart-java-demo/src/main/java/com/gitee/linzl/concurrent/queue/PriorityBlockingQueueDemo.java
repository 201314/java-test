package com.gitee.linzl.concurrent.queue;

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueDemo {
    public static class Task implements Comparable<Task> {
        private int id;
        private String name;

        public Task(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public int compareTo(Task task) {
            return this.id > task.id ? 1 : (this.id < task.id ? -1 : 0);
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public static void main(String[] args) throws Exception {
        PriorityBlockingQueue<Task> q = new PriorityBlockingQueue<>();

        Task t1 = new Task(3, "id为3");
        Task t2 = new Task(2, "id为2");
        Task t3 = new Task(1, "id为1");
        Task t5 = new Task(5, "id为5");
        Task t4 = new Task(4, "id为4");
        Task t6 = new Task(0, "id为0");

        q.add(t1); // 3
        System.out.println("容器0：" + q);
        q.add(t2); // 2
        System.out.println("容器1：" + q);
        q.add(t3); // 1
        System.out.println("容器2：" + q);
        q.add(t5); // 5
        System.out.println("容器3：" + q);
        q.add(t4); // 4
        System.out.println("容器4：" + q);
        q.add(t6);
        System.out.println("容器5：" + q);
        for (int i = 0; i < 5; i++) {
            System.out.println(q.take().getId());
        }
    }
}
