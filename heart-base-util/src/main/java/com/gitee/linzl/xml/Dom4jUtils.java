package com.gitee.linzl.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Attribute;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

/**
 * 读写性能最佳 依赖dom4j.jar、jaxen-1.1-beta-4.jar 对于复杂格式的xml更合适
 * 
 * @author linzl 最后修改时间：2014年10月10日
 */
public class Dom4jUtils {

	/**
	 * 使用JDK自带的类转换Document对象转成字符串
	 * 
	 * @param doc
	 * @return
	 * @throws TransformerException
	 */
	public static String toString(Document doc) throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, doc.getXMLEncoding());
		// 设置格式换行
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// 此处代码 和JDK本身的dom稍有不同
		transformer.transform(new DocumentSource(doc), new StreamResult(bos));
		return bos.toString();
	}

	/**
	 * Document对象格式化成字符串
	 * 
	 * @param doc
	 * @return
	 * @throws IOException
	 */
	public static String formatToString(Document doc) throws IOException {
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		// xml自身的编码
		outformat.setEncoding(doc.getXMLEncoding());
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XMLWriter writer = new XMLWriter(bos, outformat);
		writer.write(doc);
		writer.flush();
		return bos.toString();
	}

	/**
	 * Document对象转成xml字符串
	 * 
	 * @param doc
	 * @return
	 */
	public static String docAsXML(Document doc) {
		return doc.asXML();
	}

	/**
	 * xml文件转Document 有时该方法未必能用， 建议使用stringToDoc将文件读成string后再转
	 * 
	 * @param file
	 * @return
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 */
	public static Document toDoc(File file) throws DocumentException, FileNotFoundException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(file);
		doc.normalize();
		return doc;
	}

	/**
	 * xml文件转Document 有时该方法未必能用， 建议使用stringToDoc将文件读成string后再转
	 * 
	 * @param is
	 * @return
	 * @throws DocumentException
	 */
	public static Document toDoc(InputStream is) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(is);
		doc.normalize();
		return doc;
	}

	/**
	 * 字符串内容转换成Document对象
	 * 
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	public static Document toDoc(String xmlContent) throws DocumentException {
		return DocumentHelper.parseText(xmlContent);
	}

	/**
	 * 将xmlStr格式化输出到目标文件
	 * 
	 * @param doc
	 * @param targetFile
	 * @throws IOException
	 */
	public static void formatToFile(Document doc, File targetFile) throws IOException {
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		// xml自身的编码
		outformat.setEncoding(doc.getXMLEncoding());
		outformat.setIndent(true);

		// 将xmlStr输出到目标路径，使用xml文件的头文件编码
		OutputStream out = new FileOutputStream(targetFile);
		XMLWriter writer = new XMLWriter(out, outformat);
		// 是否转义特殊字符，默认true转义，false表示不转义
		writer.setEscapeText(false);
		writer.write(doc);
		writer.flush();
	}

	/**
	 * 将xmlContent格式化输出到目标文件
	 * 
	 * @param xmlContent
	 * @param targetFile
	 * @throws TransformerException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws DocumentException
	 */
	public static void formatToFile(String xmlContent, File targetFile)
			throws TransformerException, ParserConfigurationException, SAXException, IOException, DocumentException {
		Document doc = toDoc(xmlContent);
		formatToFile(doc, targetFile);
	}

	/**
	 * 将xml文件格式化输出到目标文件
	 * 
	 * @param srcFile
	 *            xml源文件
	 * @param targetFile
	 *            格式化输出xml文件
	 * @throws TransformerException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws DocumentException
	 */
	public static void formatToFile(File srcFile, File targetFile)
			throws TransformerException, ParserConfigurationException, SAXException, IOException, DocumentException {
		Document doc = toDoc(srcFile);
		formatToFile(doc, targetFile);
	}

	/**
	 * Dom4j 的Dom的生成： 直接按照从上到下的结点顺序addElement
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Document createXML(NodeVo vo) throws IOException {
		Document doc = DocumentHelper.createDocument();
		// 节点注释, xml规范，注释在前
		if (vo.getComment() != null) {
			doc.addComment(vo.getComment());
		}
		Element root = doc.addElement(vo.getName());
		addChildsToNode(root, vo.getChildNodes());
		return doc;
	}

	/**
	 * 向某个element添加孩子结点
	 * 
	 * @param element
	 * @param childNodes
	 */
	public static void addChildsToNode(Element element, List<NodeVo> childNodes) {
		if (element == null || childNodes == null) {
			return;
		}

		for (NodeVo nodeVo : childNodes) {
			// xml规范，注释在前
			if (nodeVo.getComment() != null) {// 节点注释
				element.addComment(nodeVo.getComment());
			}
			// 节点名称
			Element childElement = element.addElement(nodeVo.getName());
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
			addChildsToNode(childElement, nodeVo.getChildNodes());
		}
	}

	/**
	 * 从根结点开始递归出所有节点信息
	 * 
	 * @param doc
	 */
	public static NodeVo recursiveDoc(Document doc) {
		NodeVo rootNode = new NodeVo();

		Iterator<Node> nodeIter = doc.nodeIterator();
		while (nodeIter.hasNext()) {
			Node node = nodeIter.next();
			if (node == null || (node.getNodeType() != Node.COMMENT_NODE && node.getNodeType() != Node.ELEMENT_NODE)) {
				continue;
			}

			// xml规范，先写注释，再写节点，所以遇到元素节点，信息就已经完整了
			if (node.getNodeType() == Node.COMMENT_NODE) {
				Comment comment = (Comment) node;
				rootNode.setComment(comment.getText());
			} else if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				rootNode.setName(child.getName());
				rootNode.setText(child.getTextTrim());
				// 首先获取当前节点的所有属性节点
				Iterator<Attribute> attrIter = child.attributeIterator();

				// 遍历属性节点
				Map<String, String> map = new LinkedHashMap<String, String>();
				while (attrIter.hasNext()) {
					Attribute attribute = attrIter.next();
					map.put(attribute.getName(), attribute.getValue());
				}
				rootNode.setAttributeMap(map);
			}
		}
		List<NodeVo> childNodes = recursiveElement(doc.getRootElement());
		rootNode.setChildNodes(childNodes);
		return rootNode;
	}

	/**
	 * 从某个元素开始递归出所有节点信息
	 * 
	 * @param element
	 */
	public static List<NodeVo> recursiveElement(Element element) {
		List<NodeVo> childList = new LinkedList<NodeVo>();
		NodeVo nodeVo = new NodeVo();

		// iter = element.elementIterator();
		Iterator<Node> nodeIter = element.nodeIterator();
		while (nodeIter.hasNext()) {
			Node node = nodeIter.next();
			if (node == null || (node.getNodeType() != Node.COMMENT_NODE && node.getNodeType() != Node.ELEMENT_NODE)) {
				continue;
			}

			// xml规范，先写注释，再写节点，所以遇到元素节点，信息就已经完整了
			if (node.getNodeType() == Node.COMMENT_NODE) {
				Comment comment = (Comment) node;
				nodeVo.setComment(comment.getText());
			} else if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				nodeVo.setName(child.getName());
				nodeVo.setText(child.getTextTrim());
				// 首先获取当前节点的所有属性节点
				Iterator<Attribute> attrIter = child.attributeIterator();

				// 遍历属性节点
				Map<String, String> map = new LinkedHashMap<String, String>();
				while (attrIter.hasNext()) {
					Attribute attribute = attrIter.next();
					map.put(attribute.getName(), attribute.getValue());
				}
				nodeVo.setAttributeMap(map);

				List<NodeVo> childNodes = recursiveElement(child);
				nodeVo.setChildNodes(childNodes);

				childList.add(nodeVo);
				nodeVo = new NodeVo();
			}
		}
		return childList;
	}

	/**
	 * 查找节点，返回符合条件的第一个节点
	 * 
	 * @param express
	 *            所查节点的表达式
	 * @param source
	 *            所查找的资源
	 * @return
	 */
	public static Node selectSingleNode(String express, Object node) {
		if (node instanceof Node) {
			return ((Node) node).selectSingleNode(express);
		}
		return null;
	}

	/**
	 * 查找节点，返回符合条件的节点集。
	 * 
	 * @param express
	 *            所查节点的表达式
	 * @param source
	 *            所查找的资源
	 * @return
	 */
	public static List selectNodes(String express, Object node) {
		if (node instanceof Node) {
			return ((Node) node).selectNodes(express);
		}
		return null;
	}

	public static void deleteNode(Document doc) throws Exception {
		Node node = selectSingleNode("/root/first/first211[@age11='永远18岁11']", doc);
		node.getParent().remove(node);
		formatToFile(doc, new File("D:/dom4j3.xml"));
	}

	public static void main(String[] args) throws Exception {
		InputStream is = Dom4jUtils.class.getResourceAsStream("/com/linzl/cn/xml/xmlRead.xml");

		is = Dom4jUtils.class.getClassLoader().getResourceAsStream("com/linzl/cn/xml/xmlRead.xml");
		// testCreate();
		// is = new FileInputStream("D://dom4j.xml");
		// Document doc = toDoc(is);
		// NodeVo node = recursiveDoc(doc);
		// Document doc3 = createXML(node);
		// formatToFile(doc, new File("D:/dom4j2.xml"));
		findNode();
		// deleteNode(doc);
	}

	public static void testCreate() throws Exception {
		NodeVo vo = new NodeVo("root");
		vo.setComment("测试根节点注释");
		List childNodes = new ArrayList();

		NodeVo child = new NodeVo("first");
		child.setComment("第一条测试内容注释");
		List firstChildList = new ArrayList();
		NodeVo child11 = new NodeVo("first211", "33'好的第一条内容21122");
		child11.setComment("第一条测试内容注释211");
		Map<String, String> attr11 = new LinkedHashMap<String, String>();
		attr11.put("age11", "永远18岁11");
		attr11.put("title11", "提示信息11");
		child11.setAttributeMap(attr11);

		NodeVo child12 = new NodeVo("first212", "第一条内容212");
		child12.setComment("第一条测试内容注释212");
		Map<String, String> attr12 = new LinkedHashMap<String, String>();
		attr12.put("age12", "永远18岁12");
		attr12.put("title12", "提示信息12");
		child12.setAttributeMap(attr12);

		firstChildList.add(child11);
		firstChildList.add(child12);
		child.setChildNodes(firstChildList);

		childNodes.add(child);

		NodeVo child2 = new NodeVo("first2", "第一条内容2");
		child2.setComment("第一条测试内容注释2");
		Map<String, String> attr = new LinkedHashMap<String, String>();
		attr.put("age", "永远18岁");
		attr.put("title", "提示信息");
		child2.setAttributeMap(attr);
		childNodes.add(child2);

		vo.setChildNodes(childNodes);
		// System.out.println(JSONHelper.toJSON(vo));
		Document doc = createXML(vo);
		formatToFile(doc, new File("D:/dom4j.xml"));
	}

	public static void findNode() throws Exception {
		InputStream is = new FileInputStream("D://测试目录//test//test.xml");

		Document doc = toDoc(is);
		// 查找属性时用 英文单词attribute @ 其他不需要
		// Node node = selectSingleNode("/root/first/first211[@age11='永远18岁11']",
		// doc);
		// System.err.println(node.getText());
		List list = selectNodes("/applys/*", doc);
		Node temp = null;
		for (int i = 0; i < list.size(); i++) {
			temp = (Node) list.get(i);
			List<Node> fileList = temp.selectNodes("files/*");
			// System.err.println("名称==>"+temp.getName()+"==>"+temp.getText());
			System.out.println("fileList==>" + fileList);
		}
	}
}
