package com.gitee.linzl.xml.strategy;

import java.util.List;

import org.dom4j.Node;

import com.gitee.linzl.xml.NodeVo;

public interface AbstractDom4jStrategy extends XmlStrategy {
	/**
	 * 向某个Node添加孩子结点
	 * 
	 * @param node
	 *            结点类型只能是Element或Document
	 * @param nodeVo
	 * @throws Exception
	 */
	public void appendToNode(Node node, NodeVo nodeVo) throws Exception;

	/**
	 * 向多个Node添加孩子结点
	 * 
	 * @param nodeList
	 *            结点类型只能是Element或Document
	 * @param nodeVo
	 * @throws Exception
	 */
	public void appendToNode(List<Node> nodeList, NodeVo nodeVo) throws Exception;

	/**
	 * 向某个Node添加孩子结点
	 * 
	 * @param node
	 *            结点类型只能是Element或Document
	 * @param nodeVoList
	 * @throws Exception
	 */
	public void appendToNode(Node node, List<NodeVo> nodeVoList) throws Exception;

	/**
	 * 向多个Node添加孩子结点
	 * 
	 * @param nodeList
	 *            结点类型只能是Element或Document
	 * @param nodeVo
	 * @throws Exception
	 */
	public void appendToNode(List<Node> nodeList, List<NodeVo> nodeVoList) throws Exception;

	/**
	 * 向某个Node之后添加兄弟结点
	 * 
	 * @param node
	 *            结点类型只能是Element或Document
	 * @param nodes
	 * @throws Exception
	 */
	public void appendAfterNode(Node node, NodeVo nodeVo) throws Exception;

	/**
	 * 向某个Node之后添加兄弟结点
	 * 
	 * @param node
	 *            结点类型只能是Element或Document
	 * @param nodeVoList
	 * @throws Exception
	 */
	public void appendAfterNode(Node node, List<NodeVo> nodeVoList) throws Exception;

	/**
	 * 向某个Node之前添加兄弟结点
	 * 
	 * @param node
	 *            结点类型只能是Element或Document
	 * @param nodeVo
	 * @throws Exception
	 */
	public void appendBeforeNode(Node node, NodeVo nodeVo) throws Exception;

	/**
	 * 向某个Node之前添加兄弟结点
	 * 
	 * @param node
	 *            结点类型只能是Element或Document
	 * @param nodeVoList
	 * @throws Exception
	 */
	public void appendBeforeNode(Node node, List<NodeVo> nodeVoList) throws Exception;

	/**
	 * 从某个元素开始递归出所有孩子节点信息
	 * 
	 * @param node
	 *            结点类型只能是Element或Document
	 * @return
	 * @throws Exception
	 */
	public List<NodeVo> recursiveFromNode(Node node) throws Exception;

	/**
	 * 全文查找节点，返回符合条件的第一个节点
	 * 
	 * @param express
	 * @return
	 * @throws Exception
	 */
	public Node selectNode(String express) throws Exception;

	/**
	 * 全文查找节点，返回符合条件的节点集。
	 * 
	 * @param express
	 * @return
	 * @throws Exception
	 */
	public List<Node> selectNodes(String express) throws Exception;

	/**
	 * 查找节点，返回符合条件的第一个节点
	 * 
	 * @param express
	 *            所查节点的表达式
	 * @param node
	 *            所查找的资源
	 * @return
	 */
	public Node selectNode(String express, Node node) throws Exception;

	/**
	 * 查找节点，返回符合条件的节点集。
	 * 
	 * @param express
	 *            所查节点的表达式
	 * @param node
	 *            所查找的资源
	 * @return
	 */
	public List<Node> selectNodes(String express, Node node) throws Exception;

	default public boolean isBranch(Node node) {
		if (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.DOCUMENT_NODE) {
				return true;
			}
		}
		return false;
	}

}
