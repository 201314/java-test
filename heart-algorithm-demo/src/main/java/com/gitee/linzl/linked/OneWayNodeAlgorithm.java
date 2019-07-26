package com.gitee.linzl.linked;

import java.util.Objects;

public class OneWayNodeAlgorithm {
	/**
	 * 单向链表反转: a->b->c-d->e->f ==> f->e->d->c->b->a
	 */
	public static OneWayNode reserve(OneWayNode node) {
		OneWayNode pre = null;
		OneWayNode cur = node;
		OneWayNode next = null;

		while (cur != null) {
			next = cur.getNext();
			cur.setNext(pre);
			pre = cur;
			cur = next;
		}
		return pre;
	}

	/**
	 * 单向链表反转:
	 * 
	 * 每2个反转 a->b->c-d->e->f ==> b->a->d->c->f->e
	 * 
	 * 每3个反转 a->b->c-d->e->f ==> c->b->a->f->e->d
	 * 
	 * ，如果最后的结点数不足K个就保持原来的链接顺序不变。
	 * 
	 * @param node
	 * @param groupStep 必然>1
	 * @return
	 */
	public static OneWayNode reserverByGroup(OneWayNode node, int groupStep) {
		OneWayNode tmp = node;
		int nodeCnt = 0;
		while (tmp != null) {
			nodeCnt++;
			tmp = tmp.getNext();
		}
		System.out.println("共有节点数：" + nodeCnt);
		OneWayNode header = new OneWayNode();
		header.setData(null);
		header.setNext(node);
		OneWayNode cur = header;
		while (nodeCnt >= groupStep) {
			OneWayNode next = cur.getNext();
			OneWayNode reserveNode = reserve(next, groupStep);
			cur.setNext(reserveNode);
			cur = next;
			nodeCnt = nodeCnt - groupStep;
		}
		return header.getNext();
	}

	private static OneWayNode reserve(OneWayNode node, int groupStep) {
		OneWayNode pre = null;
		OneWayNode cur = node;
		OneWayNode next = null;

		while (cur != null && groupStep-- > 0) {
			next = cur.getNext();
			cur.setNext(pre);
			pre = cur;
			cur = next;
		}
		node.setNext(cur);
		return pre;
	}

	/**
	 * 递归法，单向链表反转
	 * 
	 * @return
	 */
	public static OneWayNode recursionReserve(OneWayNode node) {
		if (node == null || node.getNext() == null) {// 第一个节点null,最后一个结点null
			return node;
		}
		OneWayNode next = recursionReserve(node.getNext());
		node.getNext().setNext(node);
		node.setNext(null);
		return next;
	}

	public static OneWayNode twoTwoReserve(OneWayNode node) {
		if (node == null || node.getNext() == null) {
			return node;
		}

		OneWayNode cur = node;
		OneWayNode next = null;
		OneWayNode nnext = null;

		OneWayNode connect = null;
		while (cur != null && cur.getNext() != null) {
			next = cur.getNext();
			nnext = next.getNext();

			cur.setNext(nnext);
			next.setNext(cur);

			if (connect != null) {
				connect.setNext(next);
			}
			connect = cur;
			cur = nnext;
		}
		return node.getNext();
	}

	/**
	 * 单向链表，两两反转:a->b->c->d->e->f->g->h ==> b->a->d->c->f->e->h->g
	 */
	public static OneWayNode recursionTwoTwoReserve(OneWayNode node) {
		if (node == null || node.getNext() == null) {
			return node;
		}

		OneWayNode cur = node;
		OneWayNode next = node.getNext();// 得到b
		OneWayNode nnext = next.getNext();// 得到c

		cur.setNext(nnext);// a->c
		next.setNext(cur);// b->a->c

		OneWayNode connect = recursionTwoTwoReserve(nnext);// 下一段c->去递归
		if (connect != null) {
			cur.setNext(connect);// b->a->d->c
		}
		return next;
	}

	/**
	 * 假设链表中每一个节点的值都在0-9之间，那么链表整体就可以代表一个整数。例如9->3->7，代表937.
	 * 给定两个这种链表的头节点head1和head2，请生成代表两个整数相加值的结果链表。
	 * 
	 * 例如：9->3->7和6->3，相加结果为1->0->0->0
	 * 
	 * 如果可以使用Stack会更方便
	 */
	public static OneWayNode plus(OneWayNode first, OneWayNode second) {
		OneWayNode firstData = reserve(first);
		OneWayNode secondData = reserve(second);

		OneWayNode head = new OneWayNode();
		OneWayNode next = null;

		boolean returnOne = false;// 是否需要进1
		while (firstData != null || secondData != null) {
			int f1 = firstData != null ? firstData.getNum() : 0;
			int s1 = secondData != null ? secondData.getNum() : 0;
			int plus = 0;
			if (returnOne) {
				plus = f1 + s1 + 1;
			} else {
				plus = f1 + s1;
			}
			returnOne = plus >= 10 ? true : false;
			plus = returnOne ? plus - 10 : plus;
			if (next == null) {
				next = new OneWayNode();
				next.setNum(plus);
				head.setNext(next);
			} else {
				OneWayNode nNext = new OneWayNode();
				nNext.setNum(plus);
				next.setNext(nNext);
				next = nNext;
			}

			firstData = firstData != null ? firstData.getNext() : firstData;
			secondData = secondData != null ? secondData.getNext() : secondData;
		}

		if (returnOne) {// 最高位进1
			OneWayNode nNext = new OneWayNode();
			nNext.setNum(1);
			next.setNext(nNext);
		}

		return reserve(head.getNext());
	}

	/**
	 * 假设链表中每一个节点的值都在0-9之间，那么链表整体就可以代表一个整数。例如9->3->7，代表937.
	 * 给定两个这种链表的头节点head1和head2，请生成代表两个整数相减值的结果链表。
	 * 
	 * 相减结果不考虑为负数的情况
	 * 
	 * 例如：9->3->7和6->3，相减结果为8->7->4
	 * 
	 * 如果可以使用Stack会更方便
	 */
	public static OneWayNode minus(OneWayNode first, OneWayNode second) {
		OneWayNode firstData = reserve(first);
		OneWayNode secondData = reserve(second);

		OneWayNode minusData = new OneWayNode();
		OneWayNode nextData = null;

		boolean borrowOne = false;// 相减是否需要借1
		int minus = 0;
		while (firstData != null && secondData != null) {
			minus = borrowOne ? firstData.getNum() - 1 - secondData.getNum() : firstData.getNum() - secondData.getNum();

			borrowOne = (minus > 0) ? false : true;// minus如果是负数，表示被减数<减数,需要借1
			minus = borrowOne ? minus + 10 : minus;

			OneWayNode next = new OneWayNode();
			next.setNum(minus);
			if (nextData != null) {
				nextData.setNext(next);
			} else {
				minusData.setNext(next);
			}
			nextData = next;
			firstData = firstData.getNext();
			secondData = secondData.getNext();
		}

		while (firstData != null) {
			if (borrowOne) {// 借1
				minus = firstData.getNum() - 1;
			} else {
				minus = firstData.getNum();
			}

			OneWayNode next = new OneWayNode();
			next.setNum(minus);
			if (nextData != null) {
				nextData.setNext(next);
			} else {
				minusData.setNext(next);
			}
			nextData = next;
			firstData = firstData.getNext();
		}
		while (secondData != null) {
			if (borrowOne) {// 借1
				minus = secondData.getNum() - 1;
			} else {
				minus = secondData.getNum();
			}

			OneWayNode next = new OneWayNode();
			next.setNum(minus);
			if (nextData != null) {
				nextData.setNext(next);
			} else {
				minusData.setNext(next);
			}
			nextData = next;
			secondData = secondData.getNext();
		}

		return reserve(minusData.getNext());
	}

	/**
	 * 两个线性表(降序排好)，将其合并并去重
	 * 
	 * 例如：9->7->6->3->1和8->7->5->3->2->1，合并后结果为9->8->7->6->5->3->2->1
	 */
	public static OneWayNode merge(OneWayNode first, OneWayNode second) {
		OneWayNode headOne = first;
		OneWayNode headTwo = second;

		if (Objects.isNull(headOne) && Objects.nonNull(headTwo)) {
			return headTwo;
		}
		if (Objects.nonNull(headOne) && Objects.isNull(headTwo)) {
			return headOne;
		}
		if (Objects.isNull(headOne) && Objects.isNull(headTwo)) {
			return null;
		}

		OneWayNode merged = new OneWayNode();
		OneWayNode next = null;
		while (headOne != null && headTwo != null) {
			int nNum = 0;
			if (headOne.getNum() > headTwo.getNum()) {
				nNum = headOne.getNum();
				headOne = headOne.getNext();
			} else if (headOne.getNum() < headTwo.getNum()) {
				nNum = headTwo.getNum();
				headTwo = headTwo.getNext();
			} else {// 如果刚好相等
				nNum = headOne.getNum();
				headOne = headOne.getNext();
				headTwo = headTwo.getNext();
			}

			if (Objects.isNull(next)) {
				next = new OneWayNode();
				next.setNum(nNum);
				merged.setNext(next);
			} else {
				OneWayNode nNext = new OneWayNode();
				nNext.setNum(nNum);
				next.setNext(nNext);
				next = nNext;
			}
		}
		return merged.getNext();
	}
}
