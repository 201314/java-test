package com.gitee.linzl.tree;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TreeNode {
	private int num;
	private TreeNode leftNode;
	private TreeNode rightNode;

	public TreeNode(int num) {
		this.num = num;
	}
}
