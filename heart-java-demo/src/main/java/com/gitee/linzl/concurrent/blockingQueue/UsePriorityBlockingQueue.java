package com.gitee.linzl.concurrent.blockingQueue;

import java.util.concurrent.PriorityBlockingQueue;

public class UsePriorityBlockingQueue {
	public static void main(String[] args) throws Exception {
		PriorityBlockingQueue<Task> q = new PriorityBlockingQueue<>();

		Task t1 = new Task();
		t1.setId(3);
		t1.setName("id为3");

		Task t2 = new Task();
		t2.setId(2);
		t2.setName("id为2");

		Task t3 = new Task();
		t3.setId(1);
		t3.setName("id为1");

		Task t5 = new Task();
		t5.setId(5);
		t5.setName("id为5");

		Task t4 = new Task();
		t4.setId(4);
		t4.setName("id为4");

		Task t0 = new Task();
		t0.setId(0);
		t0.setName("id为0");

		// return this.id > task.id ? 1 : 0;

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
		q.add(t0);
		// 1 3 4
		System.out.println("容器5：" + q);
		for (int i = 0; i < 5; i++) {
			System.out.println(q.take().getId());
		}
	}
}
