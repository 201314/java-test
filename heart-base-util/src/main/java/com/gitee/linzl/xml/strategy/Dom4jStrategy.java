package com.gitee.linzl.xml.strategy;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLResult;
import org.dom4j.io.XMLWriter;

import com.gitee.linzl.xml.NodeVo;

/**
 * 读写性能最佳 依赖dom4j.jar、jaxen-1.1-beta-4.jar 对于复杂格式的xml更合适
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年9月4日
 */
public class Dom4jStrategy implements AbstractDom4jStrategy {
	private Document doc;

	public Document getDoc() {
		return doc;
	}

	public Dom4jStrategy() {
		this.doc = DocumentHelper.createDocument(DocumentHelper.createElement("root"));
	}

	public Dom4jStrategy(File file) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		doc.normalize();
		this.doc = doc;
	}

	public Dom4jStrategy(URL url) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(url);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		doc.normalize();
		this.doc = doc;
	}

	public Dom4jStrategy(InputStream in) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(in);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		doc.normalize();
		this.doc = doc;
	}

	public Dom4jStrategy(Reader reader) {
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			doc = saxReader.read(reader);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		doc.normalize();
		this.doc = doc;
	}

	public Dom4jStrategy(String xmlContent) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlContent);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		doc.normalize();
		this.doc = doc;
	}

	public String readAsText() {
		return readAsText(false);
	}

	public String readAsText(boolean format) {
		try {
			OutputFormat outformat = null;
			if (format) {
				outformat = OutputFormat.createPrettyPrint();
			} else {
				outformat = new OutputFormat();
				outformat.setTrimText(true);
			}

			// xml自身的编码
			outformat.setEncoding(doc.getXMLEncoding());
			StringWriter bos = new StringWriter();
			XMLResult result = new XMLResult(bos, outformat);
			// XMLWriter writer = new XMLWriter(bos, outformat);
			XMLWriter writer = result.getXMLWriter();
			writer.write(doc);
			writer.flush();
			return bos.toString();
		} catch (Exception e) {

		}
		return doc.asXML();
	}

	public void write(File file) throws Exception {
		write(file, false);
	}

	public void write(File file, boolean format) throws Exception {
		OutputFormat outformat = null;
		if (format) {
			// xml自身的编码
			outformat = OutputFormat.createPrettyPrint();
		} else {
			outformat = new OutputFormat();
			outformat.setTrimText(true);
		}
		outformat.setEncoding(doc.getXMLEncoding());
		OutputStreamWriter outWriter = new FileWriter(file);
		XMLResult result = new XMLResult(outWriter, outformat);
		// XMLWriter writer = new XMLWriter(outWriter, outformat);
		XMLWriter writer = result.getXMLWriter();
		writer.write(doc);
		writer.flush();
	}

	public void appendToRoot(NodeVo vo) throws Exception {
		Document doc = this.doc;
		// 节点注释, xml规范，注释在前
		if (vo.getComment() != null) {
			doc.addComment(vo.getComment());
		}
		Element root = doc.addElement(vo.getName());
		appendToNode(root, vo.getChildNodes());
	}

	public void appendToRoot(List<NodeVo> nodeVoList) throws Exception {
		Document doc = this.doc;
		appendToNode(doc.getRootElement(), nodeVoList);
	}

	public void appendToNode(String express, NodeVo nodeVo) throws Exception {
		Document doc = this.doc;
		Number number = doc.numberValueOf(express);
		if (number != null) {
			if (number.intValue() == 1) {// 查找到只有单个结点
				Node node = doc.selectSingleNode(express);
				appendToNode(node, nodeVo);
			} else if (number.intValue() > 1) {// 有多个结点
				List<Node> nodes = doc.selectNodes(express);
				appendToNode(nodes, nodeVo);
			}
		}
	}

	public void appendToNode(String express, List<NodeVo> nodeVoList) throws Exception {
		Document doc = this.doc;
		Number number = doc.numberValueOf(express);
		if (number != null) {
			if (number.intValue() == 1) {// 查找到只有单个结点
				Node node = doc.selectSingleNode(express);
				appendToNode(node, nodeVoList);
			} else if (number.intValue() > 1) {// 有多个结点
				List<Node> nodes = doc.selectNodes(express);
				appendToNode(nodes, nodeVoList);
			}
		}
	}

	public void appendToNode(Node node, NodeVo nodeVo) throws Exception {
		if (nodeVo == null || !isBranch(node)) {
			return;
		}

		Branch branch = (Branch) node;

		// xml规范，注释在前
		if (nodeVo.getComment() != null) {// 节点注释
			branch.add(DocumentHelper.createComment(nodeVo.getComment()));
		}
		// 节点名称
		Element childElement = branch.addElement(nodeVo.getName());
		if (nodeVo.getText() != null) {
			// 节点文本内容
			childElement.setText(nodeVo.getText());
		}
		// 节点属性
		Map<String, String> attr = nodeVo.getAttributeMap();
		if (attr != null && !attr.isEmpty()) {
			Set<String> set = attr.keySet();
			Iterator<String> iter = set.iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				String value = attr.get(key);
				childElement.addAttribute(key, value);
			}
		}
		appendToNode(childElement, nodeVo.getChildNodes());
	}

	public void appendToNode(List<Node> nodeList, NodeVo nodeVo) throws Exception {
		if (nodeList != null && nodeList.size() > 0) {
			nodeList.forEach((Node node) -> {
				try {
					appendToNode(node, nodeVo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	public void appendToNode(Node node, List<NodeVo> nodeVoList) throws Exception {
		if (nodeVoList != null && nodeVoList.size() > 0) {
			nodeVoList.forEach((NodeVo nodeVo) -> {
				try {
					appendToNode(node, nodeVo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	public void appendToNode(List<Node> nodeList, List<NodeVo> nodeVoList) throws Exception {
		if (nodeList != null && nodeList.size() > 0) {
			nodeList.forEach((Node node) -> {
				try {
					appendToNode(node, nodeVoList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	public void appendAfterNode(Node node, NodeVo nodeVo) throws Exception {
		if (nodeVo == null || !isBranch(node)) {
			return;
		}

		Branch parent = node.getParent();
		if (parent == null) {
			return;
		}
		int curIndex = parent.indexOf(node);

		// 在文件节点添加兄弟结点
		while (++curIndex < parent.nodeCount()) {
			if ((parent.node(curIndex)).getNodeType() == Node.TEXT_NODE) {
				// 创建一个虚拟的结点
				Element nextSibling = DocumentHelper.createElement("nextSiblingElement");
				appendToNode(nextSibling, nodeVo);
				parent.content().addAll(curIndex + 1, nextSibling.content());
				break;
			}
		}
	}

	public void appendAfterNode(Node node, List<NodeVo> nodeVoList) throws Exception {
		if (nodeVoList == null || !isBranch(node)) {
			return;
		}

		Branch parent = node.getParent();
		if (parent == null) {
			return;
		}
		int curIndex = parent.indexOf(node);

		// 在文件节点添加兄弟结点
		while (++curIndex < parent.nodeCount()) {
			if ((parent.node(curIndex)).getNodeType() == Node.TEXT_NODE) {
				// 创建一个虚拟的结点
				Element nextSibling = DocumentHelper.createElement("nextSiblingElement");
				appendToNode(nextSibling, nodeVoList);
				parent.content().addAll(curIndex + 1, nextSibling.content());
				break;
			}
		}
	}

	public void appendBeforeNode(Node node, NodeVo nodeVo) throws Exception {
		if (nodeVo == null || !isBranch(node)) {
			return;
		}

		Branch parent = node.getParent();
		if (parent == null) {
			return;
		}
		int curIndex = parent.indexOf(node);

		// 找到兄弟元素节点添加
		while (--curIndex >= 0) {
			if ((parent.node(curIndex)).getNodeType() == Node.ELEMENT_NODE
					|| (curIndex == 0 && parent.node(curIndex).getNodeType() == Node.TEXT_NODE)) {
				// 创建一个虚拟的结点
				Element nextSibling = DocumentHelper.createElement("brotherElement");
				appendToNode(nextSibling, nodeVo);
				parent.content().addAll(curIndex, nextSibling.content());
				break;
			}
		}
	}

	public void appendBeforeNode(Node node, List<NodeVo> nodeVoList) throws Exception {
		if (nodeVoList == null || !isBranch(node)) {
			return;
		}

		Branch parent = node.getParent();
		if (parent == null) {
			return;
		}
		int curIndex = parent.indexOf(node);

		// 找到兄弟元素节点添加
		while (--curIndex >= 0) {
			if ((parent.node(curIndex)).getNodeType() == Node.ELEMENT_NODE
					|| (curIndex == 0 && parent.node(curIndex).getNodeType() == Node.TEXT_NODE)) {
				// 创建一个虚拟的结点
				Element nextSibling = DocumentHelper.createElement("brotherElement");
				appendToNode(nextSibling, nodeVoList);
				parent.content().addAll(curIndex, nextSibling.content());
				break;
			}
		}
	}

	public NodeVo recursiveFromRoot() throws Exception {
		Document doc = this.doc;
		List<NodeVo> nodeVo = recursiveFromNode(doc);
		return nodeVo == null ? null : nodeVo.get(0);
	}

	public List<NodeVo> recursiveFromNode(Node node) throws Exception {
		if (node == null) {
			return Collections.emptyList();
		}

		Branch branch = null;
		if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.DOCUMENT_NODE) {
			branch = (Branch) node;
		}

		List<NodeVo> childList = new LinkedList<>();
		NodeVo nodeVo = new NodeVo();

		Iterator<Node> nodeIter = branch.nodeIterator();
		// 一个节点包含注释、元素
		while (nodeIter.hasNext()) {
			Node currNode = nodeIter.next();
			if (currNode == null
					|| (currNode.getNodeType() != Node.COMMENT_NODE && currNode.getNodeType() != Node.ELEMENT_NODE)) {
				continue;
			}

			// xml规范，先写注释，再写节点，所以遇到元素节点，信息就已经完整了
			if (currNode.getNodeType() == Node.COMMENT_NODE) {
				if (StringUtils.isNotEmpty(currNode.getText())) {
					nodeVo.setComment(currNode.getText());
				}
			} else if (currNode.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) currNode;
				nodeVo.setName(child.getName());
				if (StringUtils.isNotEmpty(child.getTextTrim())) {
					nodeVo.setText(child.getTextTrim());
				}
				// 首先获取当前节点的所有属性节点
				Iterator<Attribute> attrIter = child.attributeIterator();
				// 遍历属性节点
				Map<String, String> map = null;
				while (attrIter.hasNext()) {
					if (map == null) {
						map = new HashMap<>();
					}
					Attribute attribute = attrIter.next();
					map.put(attribute.getName(), attribute.getValue());
				}
				nodeVo.setAttributeMap(map);

				List<NodeVo> childNodes = recursiveFromNode(child);
				if (childNodes != null && childNodes.size() > 0) {
					nodeVo.setChildNodes(childNodes);
				}

				childList.add(nodeVo);
				nodeVo = new NodeVo();
			}
		}
		return childList;
	}

	public Node selectNode(String express) {
		return selectNode(express, doc);
	}

	public List<Node> selectNodes(String express) {
		return selectNodes(express, doc);
	}

	public Node selectNode(String express, Node node) {
		return node.selectSingleNode(express);
	}

	public List<Node> selectNodes(String express, Node node) {
		return node.selectNodes(express);
	}

	// public void deleteNode(Document doc) throws Exception {
	// Node node = selectSingleNode("/root/first/first211[@age11='永远18岁11']", doc);
	// node.getParent().remove(node);
	// formatToFile(doc, new File("D:/dom4j3.xml"));
	// }
}
