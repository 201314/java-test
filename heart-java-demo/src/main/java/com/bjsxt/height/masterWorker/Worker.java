package com.bjsxt.height.masterWorker;

import java.util.Map;
import java.util.Queue;

public abstract class Worker implements Runnable {
	private Queue<Task> workerQueue;
	private Map<String, Object> resultMap;

	public void setWorkerQueue(Queue<Task> workQueue) {
		this.workerQueue = workQueue;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	@Override
	public void run() {
		while (true) {
			Task input = this.workerQueue.poll();
			if (input == null) {
				break;
			}
			// 真正的去做业务处理
			Object output = handle(input);
			this.resultMap.put(Integer.toString(input.getId()), output);
		}
	}

	public abstract Object handle(Task input);
}
