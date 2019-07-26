package com.gitee.linzl.tree;

import org.junit.Test;

public class TreeAlgorithmTest {
	/**
	 * <p>
	 * 6 / \ 3 5 / \ / \ 1 4 8 7 / / 2 9
	 * </p>
	 */
	@Test
	public void testPriOrder() {
		TreeNode root = new TreeNode(6);

		TreeNode rleft = new TreeNode(3);
		TreeNode rlleft = new TreeNode(1);
		TreeNode rlRight = new TreeNode(4);
		rleft.setLeftNode(rlleft);
		rleft.setRightNode(rlRight);

		TreeNode rlRleft = new TreeNode(2);
		rlRight.setLeftNode(rlRleft);

		TreeNode rRight = new TreeNode(5);
		TreeNode rRleft = new TreeNode(8);
		TreeNode rRRight = new TreeNode(7);
		rRight.setLeftNode(rRleft);
		rRight.setRightNode(rRRight);

		TreeNode rRRleft = new TreeNode(9);
		rRRight.setLeftNode(rRRleft);

		root.setLeftNode(rleft);
		root.setRightNode(rRight);
//		TreeAlgorithm.recursionPreOrder(root);
//		TreeAlgorithm.preOrder(root);

//		TreeAlgorithm.recursionByMidOrder(root);
//		TreeAlgorithm.midOrder(root);

//		TreeAlgorithm.recursionByAfterOrder(root);
//		TreeAlgorithm.afterOrder(root);

//		TreeAlgorithm.printByLevel(root);
//		TreeAlgorithm.calTreeDepth(root);
		TreeAlgorithm.calTreeWidth(root);
//		TreeAlgorithm.isCompletelyBinaryTree(root);
	}

	@Test
	public void isCompletelyBinaryTree() {
		TreeNode root = new TreeNode(6);

		TreeNode rleft = new TreeNode(3);
		TreeNode rlleft = new TreeNode(1);
		TreeNode rlRight = new TreeNode(4);
		rleft.setLeftNode(rlleft);
		rleft.setRightNode(rlRight);

		TreeNode rlRleft = new TreeNode(2);
		rlRight.setLeftNode(rlRleft);

		TreeNode rRight = new TreeNode(5);
		TreeNode rRleft = new TreeNode(8);
		TreeNode rRRight = new TreeNode(7);
		rRight.setLeftNode(rRleft);
		rRight.setRightNode(rRRight);

//		TreeNode rRRleft = new TreeNode(9);
//		rRRight.setLeftNode(rRRleft);

		root.setLeftNode(rleft);
		root.setRightNode(rRight);
//		TreeAlgorithm.isCompletelyBinaryTree(root);
		TreeAlgorithm.isFullBinaryTree(root);
	}

	@Test
	public void test90() {
		System.out.println(Integer.valueOf("1999").hashCode());
		System.out.println(Integer.valueOf("2000").hashCode());
	}
}
