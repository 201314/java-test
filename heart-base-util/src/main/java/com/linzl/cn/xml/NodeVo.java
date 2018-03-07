package com.linzl.cn.xml;

import java.util.List;
import java.util.Map;

public class NodeVo {
	/**
	 * 节点注释
	 */
	private String comment;

	/**
	 * 节点名称
	 */
	private String name;

	/**
	 * 节点属性键值对
	 */
	private Map<String, String> attributeMap;

	/**
	 * 节点文本内容
	 */
	private String text;

	/**
	 * 节点的孩子节点
	 */
	private List<NodeVo> childNodes;

	public NodeVo() {

	}

	public NodeVo(String name) {
		this(name, null, null);
	}

	public NodeVo(String name, String text) {
		this(name, text, null);
	}

	public NodeVo(String name, String text, String comment) {
		this.name = name;
		this.text = text;
		this.comment = comment;
	}

	/**
	 * 节点注释
	 * 
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 节点注释
	 * 
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 节点名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 节点名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 节点属性键值对
	 * 
	 * @return
	 */
	public Map<String, String> getAttributeMap() {
		return attributeMap;
	}

	/**
	 * 节点属性键值对
	 * 
	 * @param attributeMap
	 */
	public void setAttributeMap(Map<String, String> attributeMap) {
		this.attributeMap = attributeMap;
	}

	/**
	 * 节点文本内容
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * 节点文本内容
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 节点的孩子节点
	 * 
	 * @return
	 */
	public List<NodeVo> getChildNodes() {
		return childNodes;
	}

	/**
	 * 节点的孩子节点
	 * 
	 * @param childNodes
	 */
	public void setChildNodes(List<NodeVo> childNodes) {
		this.childNodes = childNodes;
	}

}
