package com.bjsxt.height.masterWorker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {
	// 1、 有一个盛放任务的容器
	private ConcurrentLinkedQueue<Task> workQueue = new ConcurrentLinkedQueue<>();

	// 2、子任务队列
	private Map<String, Thread> threadMap = new HashMap<>();

	// 3 、需要有一个盛放每一个worker执行任务的结果集合
	private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

	// 4 构造方法
	public Master(Worker worker, int workerCount) {
		// 每一个worker对象都需要有Master的引用 workQueue用于任务的领取，resultMap用于任务的提交
		worker.setWorkerQueue(this.workQueue);
		worker.setResultMap(this.resultMap);
		for (int i = 0; i < workerCount; i++) {
			// key表示每一个worker的名字, value表示线程执行对象
			threadMap.put("子节点" + Integer.toString(i), new Thread(worker));
		}
	}

	// 5 提交方法
	public void submit(Task task) {
		this.workQueue.add(task);
	}

	// 6 需要有一个执行的方法（启动应用程序 让所有的worker工作）
	public void execute() {
		for (Map.Entry<String, Thread> me : threadMap.entrySet()) {
			me.getValue().start();
		}
	}

	// 7 判断线程是否执行完毕
	public boolean isComplete() {
		for (Map.Entry<String, Thread> me : threadMap.entrySet()) {
			if (me.getValue().getState() != Thread.State.TERMINATED) {
				return false;
			}
		}
		return true;
	}

	// 8 计算结果方法
	public int getResult() {// 此处可以改为同时在计算，不需要等待最终Worker执行完成
		int priceResult = 0;
		for (Map.Entry<String, Object> me : resultMap.entrySet()) {
			priceResult += (Integer) me.getValue();
		}
		return priceResult;
	}
}
