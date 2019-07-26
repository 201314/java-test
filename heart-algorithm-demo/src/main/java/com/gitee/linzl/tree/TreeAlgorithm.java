package com.gitee.linzl.tree;

import java.util.LinkedList;
import java.util.Objects;

/**
 * 所谓的先序中序后序都是依据根节点打印顺序来说的
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年7月15日
 */
public class TreeAlgorithm {
	/**
	 * 按先序遍历节点递归输出
	 * 
	 * @param root
	 */
	public static void recursionPreOrder(TreeNode root) {
		if (Objects.isNull(root)) {
			return;
		}
		System.out.print(root.getNum() + "\t");
		recursionPreOrder(root.getLeftNode());
		recursionPreOrder(root.getRightNode());
	}

	/**
	 * 在压栈的时候，一直都是先压左子树，因为在先序遍历中，左孩子一定在右孩子的前面
	 * 对于前序遍历，先打印根节点，因为压栈就是从根节点开始的，一直压左孩子，当左孩子为空时，就让栈顶元素出栈，并开始压右孩子
	 * 
	 * @param root
	 */
	public static void preOrder(TreeNode root) {
		LinkedList<TreeNode> list = new LinkedList<>();
		TreeNode cur = root;
		while (Objects.nonNull(cur) || !list.isEmpty()) {
			while (Objects.nonNull(cur)) {
				System.out.print(cur.getNum() + "\t");
				list.add(cur);
				cur = cur.getLeftNode();// 先序遍历，先把左边元素压栈
			}
			// 以上操作把所有左边元素压栈完毕
			// 弹出最后一个元素，寻找该元素的右边结点的左边元素
			cur = list.pollLast().getRightNode();
		}
	}

	/**
	 * 按中序遍历节点输出
	 * 
	 * @param root
	 */
	public static void recursionByMidOrder(TreeNode root) {
		if (Objects.isNull(root)) {
			return;
		}
		recursionByMidOrder(root.getLeftNode());
		System.out.print(root.getNum() + "\t");
		recursionByMidOrder(root.getRightNode());
	}

	public static void midOrder(TreeNode root) {
		LinkedList<TreeNode> list = new LinkedList<>();
		TreeNode cur = root;
		while (Objects.nonNull(cur) || !list.isEmpty()) {
			while (Objects.nonNull(cur)) {
				list.add(cur);
				cur = cur.getLeftNode();
			}
			TreeNode pop = list.pollLast();
			System.out.print(pop.getNum() + "\t");
			cur = pop.getRightNode();
		}
	}

	/**
	 * 按后序遍历节点输出
	 * 
	 * @param root
	 */
	public static void recursionByAfterOrder(TreeNode root) {
		if (Objects.isNull(root)) {
			return;
		}
		recursionByAfterOrder(root.getLeftNode());
		recursionByAfterOrder(root.getRightNode());
		System.out.print(root.getNum() + "\t");
	}

	public static void afterOrder(TreeNode root) {
		LinkedList<TreeNode> list = new LinkedList<>();
		TreeNode cur = root;
		while (Objects.nonNull(cur) || !list.isEmpty()) {
			while (Objects.nonNull(cur)) {
				list.add(cur);
				cur = cur.getLeftNode();
			}
			TreeNode pop = list.pollLast();
			cur = pop.getRightNode();// 取出右边节点
			if (Objects.nonNull(cur)) {
				list.add(pop);// 后序遍历，节点还不能打印
				pop.setRightNode(null);// 将右边节点关系去掉
			} else {
				System.out.print(pop.getNum() + "\t");
			}
		}
	}

	/**
	 * 维护一个节点队列和两个节点引用last(上一行最后的元素)、.nlast(本行最后的元素)，初始化时nlast=last=root。
	 * 
	 * 不断取出队列第一个元素x，然后将x的左右孩子入队并移动nlast到最后一个孩子,然后判断x是否是last，是则打印并换行并将last指向nlast(开始下一行),否则普通打印。
	 * 
	 * 按层次输出树节点数据
	 * 
	 * @param root
	 */
	public static int printByLevel(TreeNode root) {
		LinkedList<TreeNode> list = new LinkedList<>();
		list.add(root);
		TreeNode preLast = root;// 上一行的最后一个元素
		TreeNode curLast = null;// 当前行的最后一个元素

		int depth = 0;
		while (!list.isEmpty()) {
			TreeNode cur = list.poll();
			if (cur.getLeftNode() != null) {
				list.add(cur.getLeftNode());
				curLast = list.getLast();
			}
			if (cur.getRightNode() != null) {
				list.add(cur.getRightNode());
				curLast = list.getLast();
			}
			if (cur == preLast) {
				depth++;
				System.out.print(cur.getNum() + "\n");
				preLast = curLast;
			} else {
				System.out.print(cur.getNum() + "\t");
			}
		}
		return depth;
	}

	// 必须知道有多少层
	public static void recursionByLevel() {

	}

	/**
	 * 计算树的深度，即两点间的最大距离
	 * 
	 * 可通过按层次打印计算得出
	 * 
	 * @param root
	 * @return
	 */
	public static void calTreeDepth(TreeNode root) {
		System.out.println("树的深度为:" + printByLevel(root));
	}

	/**
	 * 递归计算出树的深度2，即两点的最大距离
	 * 
	 * 可通过最左节点，最右节点
	 * 
	 * @param root
	 */
	public static int recursionTreeDepth(TreeNode node) {
		if (node == null) {
			return 0;
		}
		int leftDepth = recursionTreeDepth(node.getLeftNode());
		int rightDepth = recursionTreeDepth(node.getRightNode());
		return 1 + Math.max(leftDepth, rightDepth);
	}

	// 非递归计算
	public static int treeDepth(TreeNode node) {
		return 0;
	}

	// 计算出树的最大宽度
	public static void calTreeWidth(TreeNode root) {
		LinkedList<TreeNode> list = new LinkedList<>();
		list.add(root);
		TreeNode preLast = root;// 上一行的最后一个元素
		TreeNode curLast = null;// 当前行的最后一个元素

		int maxWidth = 0;
		int calWith = 0;
		while (!list.isEmpty()) {
			calWith++;
			TreeNode cur = list.poll();
			if (cur.getLeftNode() != null) {
				list.add(cur.getLeftNode());
				curLast = list.getLast();
			}
			if (cur.getRightNode() != null) {
				list.add(cur.getRightNode());
				curLast = list.getLast();
			}
			if (cur == preLast) {
				if (calWith > maxWidth) {
					maxWidth = calWith;
					calWith = 0;
				}
				System.out.print(cur.getNum() + "\n");
				preLast = curLast;
			} else {
				System.out.print(cur.getNum() + "\t");
			}
		}
		System.out.println("最大宽度为:" + maxWidth);
	}

	/**
	 * 判断是否为满二叉树，即是一棵完全对称的树
	 * 
	 * 深度K,每层节点数全满且为2^K方
	 * 
	 * 一棵深度为k，且有2^k-1个节点的二叉树，称为满二叉树
	 * 
	 * 
	 * @param root
	 * @return
	 */
	public static boolean isFullBinaryTree(TreeNode root) {
		LinkedList<TreeNode> list = new LinkedList<>();
		list.add(root);
		TreeNode preLast = root;// 上一行的最后一个元素
		TreeNode curLast = null;// 当前行的最后一个元素

		int depth = 0;
		int nodeNum = 0;
		while (!list.isEmpty()) {
			nodeNum++;
			TreeNode cur = list.poll();
			if (cur.getLeftNode() != null) {
				list.add(cur.getLeftNode());
				curLast = list.getLast();
			}
			if (cur.getRightNode() != null) {
				list.add(cur.getRightNode());
				curLast = list.getLast();
			}
			if (cur == preLast) {
				depth++;
				System.out.print(cur.getNum() + "\n");
				preLast = curLast;
			} else {
				System.out.print(cur.getNum() + "\t");
			}
		}
		System.out.println("树的深度:" + depth);
		System.out.println("树的结点数:" + nodeNum);
		System.out.println("是否为满二叉树：" + (nodeNum == Math.pow(2, depth) - 1));
		return nodeNum == Math.pow(2, depth) - 1;
	}

	/**
	 * 判断是否为完全二叉树
	 * 
	 * 深度K，1-(K-1)层全满，第K层最多缺失右边连续若干节点
	 * 
	 * 根据以上特点可知，NULL节点一定是连续的且之后不会再出现非NULL节点
	 * 
	 * 节点数N,则深度K= floor(log2N)+1 向下取整
	 * 
	 * @param root
	 * @return true:完全二叉树,false不是完全二叉树
	 */
	public static boolean isCompletelyBinaryTree(TreeNode root) {
		LinkedList<TreeNode> list = new LinkedList<>();
		list.add(root);

		boolean notNull = false;
		while (!list.isEmpty()) {
			TreeNode cur = list.poll();
			if (cur == null) {// 如果后续都为NULL,表示是完全二叉树
				notNull = list.stream().allMatch(node -> node == null);
				break;
			} else {
				list.add(cur.getLeftNode());// 如果节点为空，就存一个NULL
				list.add(cur.getRightNode());
			}
		}
		return notNull;
	}

	/**
	 * 判断二叉树是否对称，没有的节点也当成NULL存入
	 * 
	 * 可通过中序遍历后，看数组是否对称
	 * 
	 * @param root
	 * @return
	 */
	public boolean isSymmetricBinaryTree(TreeNode root) {
		return false;
	}

}
