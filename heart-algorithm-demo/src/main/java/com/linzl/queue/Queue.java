package com.linzl.queue;

/**
 * 用java实现一个队列，满了长度扩一倍，剩下1/4时，长度减一半
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年1月6日
 */
public class Queue {
	/** 默认长度 */
	private static final int DEFUALT_LENGTH = 10;
	/** 队列数据 */
	private Object[] objs;
	/** 队列头 */
	private int head = 0;
	/** 队列尾 */
	private int tail = 0;
	/** 队列可用长度 */
	private int length = 0;

	/** * 初期化一个默认大小的数组 */
	public Queue() {
		this.objs = new Object[DEFUALT_LENGTH];
	}

	/** * 初期化一个指定大小的数组 * * @param int */
	public Queue(int number) {
		if (number > 0) {
			this.objs = new Object[number];
		} else {
			this.objs = new Object[DEFUALT_LENGTH];
		}
	}

	/**
	 * 入列
	 * 
	 * @param obj
	 */
	public void enQueue(Object obj) {
		// 满位的场合
		if (isFull()) { // 增加队列容量
			enlarge();
			head = 0;
		}
		this.tail = (this.head + this.length) % this.objs.length;
		this.objs[this.tail] = obj;
		this.length++;
	}

	/**
	 * 出列
	 * 
	 * @return Object
	 */
	public Object deQueue() {
		if (isEmpty()) {
			return null;
		} else if (isAQuarter()) {
			reduce();
		}
		Object tempObj = this.objs[this.head];
		this.objs[this.head] = null;
		this.head = (this.head + 1) % (this.objs.length);
		this.length--;
		return tempObj;
	}

	/**
	 * 队列是否满
	 * 
	 * @return boolean
	 */
	public boolean isFull() {
		return this.length == this.objs.length;
	}

	/**
	 * 队列是否为空
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return this.length == 0;
	}

	/**
	 * 队列长度是否为原来的1/4
	 */
	public boolean isAQuarter() {
		return this.length == this.objs.length * 1 / 4;
	}

	/**
	 * 扩展数组容量
	 */
	public void enlarge() {
		Object[] tempObjs = new Object[this.objs.length * 2];
		System.arraycopy(this.objs, 0, tempObjs, 0, this.objs.length);
		this.objs = tempObjs;
	}

	/**
	 * 缩减数据容量
	 */
	public void reduce() {
		Object[] tempObjs = new Object[this.objs.length * 1 / 2];
		System.arraycopy(this.objs, 0, tempObjs, 0, tempObjs.length);
		this.objs = tempObjs;
	}
}
