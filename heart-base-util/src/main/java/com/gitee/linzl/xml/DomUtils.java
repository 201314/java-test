package com.gitee.linzl.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * w3c xml 例子 详细方法可见JDK1.6的API 文档驱动： 缺点： 在处理DOM的时候，我们需要读入整个的XML文档， 然后在内存中创建DOM树，
 * 当xml非常大的时候，很消耗内存 超过10M不适用 优点： 形成树结构，代码易编写;解析过程树结构在内存中，方便修改
 * 
 * @author linzl 最后修改时间：2014年10月10日
 */
@Deprecated
public class DomUtils {
	private static String getEncoding(String encoding) {
		return encoding != null ? encoding : "UTF-8";
	}

	/**
	 * 创建DocumentBuilder
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 */
	private static DocumentBuilder createBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder;
	}

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
		// 设置编码
		transformer.setOutputProperty(OutputKeys.ENCODING, getEncoding(doc.getXmlEncoding()));
		// 设置格式换行
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		transformer.transform(new DOMSource(doc), new StreamResult(bos));
		return bos.toString();
	}

	/**
	 * xml文件转Document
	 * 
	 * @param file
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Document toDoc(File file) throws ParserConfigurationException, SAXException, IOException {
		// 创建DocumentBuilder
		DocumentBuilder builder = createBuilder();
		// 创建Document
		Document doc = builder.parse(file);
		doc.normalize();
		return doc;
	}

	/**
	 * xml文件转Document
	 * 
	 * @param is
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Document toDoc(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		// 创建DocumentBuilder
		DocumentBuilder builder = createBuilder();
		// 创建Document
		Document doc = builder.parse(is);
		doc.normalize();
		return doc;
	}

	/**
	 * 字符串内容转换成Document对象
	 * 
	 * @param xmlStr
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document toDoc(String xmlStr) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = createBuilder();

		StringReader sr = new StringReader(xmlStr);
		InputSource is = new InputSource(sr);
		Document doc = builder.parse(is);
		doc.normalize();
		return doc;
	}

	/**
	 * 将Document格式化到目标文件
	 * 
	 * @param doc
	 * @param targetFile
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static void formatToFile(Document doc, File targetFile) throws TransformerException, IOException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		// 设置编码
		transformer.setOutputProperty(OutputKeys.ENCODING, getEncoding(doc.getXmlEncoding()));
		// 设置格式换行
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(new DOMSource(doc), new StreamResult(targetFile));
	}

	/**
	 * 将xmlStr格式化到目标文件
	 * 
	 * @param xmlStr
	 * @param targetFile
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static void formatToFile(String xmlStr, File targetFile)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document doc = toDoc(xmlStr);
		formatToFile(doc, targetFile);
	}

	/**
	 * 将xml文件格式化到目标文件
	 * 
	 * @param srcFile
	 *            xml源文件
	 * @param targetFile
	 *            格式化输出xml文件
	 * @throws TransformerException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static void formatToFile(File srcFile, File targetFile)
			throws TransformerException, ParserConfigurationException, SAXException, IOException {
		Document doc = toDoc(srcFile);
		formatToFile(doc, targetFile);
	}

	/**
	 * w3c Dom的生成： 直接按照从上到下的结点顺序创建，然后append在父结点下
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 */
	public static Document createXML(NodeVo vo) throws ParserConfigurationException {
		Document doc = createBuilder().newDocument();
		doc.setXmlStandalone(true);
		if (vo.getComment() != null) {
			// 注释内容
			Comment comment = doc.createComment(vo.getComment());
			doc.appendChild(comment);
		}
		// 根结点
		Element root = doc.createElement(vo.getName());
		addChildsToNode(doc, root, vo.getChildNodes());
		doc.appendChild(root);
		return doc;
	}

	/**
	 * 向某个element添加孩子结点
	 * 
	 * @param doc
	 *            文档结点
	 * @param element
	 * @param childNodes
	 */
	public static void addChildsToNode(Document doc, Element element, List<NodeVo> childNodes) {
		if (doc == null || element == null || childNodes == null) {
			return;
		}

		for (NodeVo nodeVo : childNodes) {
			// xml规范，注释在前
			if (nodeVo.getComment() != null) {// 节点注释
				Comment comment = doc.createComment(nodeVo.getComment());
				element.appendChild(comment);
			}
			// 节点名称
			Element childElement = doc.createElement(nodeVo.getName());
			if (nodeVo.getText() != null) {
				// 节点文本内容
				Text text = doc.createCDATASection(nodeVo.getText());
				// Text text = doc.createTextNode(nodeVo.getText());
				childElement.appendChild(text);
			}
			// 节点属性
			Map<String, String> attr = nodeVo.getAttributeMap();
			if (attr != null && !attr.isEmpty()) {
				Set<String> set = attr.keySet();
				Iterator<String> iter = set.iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					String value = attr.get(key);
					childElement.setAttribute(key, value);
				}
			}

			element.appendChild(childElement);
			addChildsToNode(doc, childElement, nodeVo.getChildNodes());
		}
	}

	/**
	 * 从根结点开始递归出所有节点信息
	 * 
	 * @param doc
	 */
	public static NodeVo recursiveDoc(Document doc) {
		NodeVo rootNode = new NodeVo();

		NodeList nodeList = doc.getChildNodes();
		for (int outIndex = 0, outLength = nodeList.getLength(); outIndex < outLength; outIndex++) {
			Node node = nodeList.item(outIndex);
			// dom会将换行和注释都做为document的节点，排除非注释、非元素节点
			if (node == null || (node.getNodeType() != Node.COMMENT_NODE && node.getNodeType() != Node.ELEMENT_NODE)) {
				continue;
			}

			// xml规范，先写注释，再写节点，所以遇到元素节点，信息就已经完整了
			if (node.getNodeType() == Node.COMMENT_NODE) {
				Comment comment = (Comment) node;
				rootNode.setComment(comment.getNodeValue());
			} else if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				rootNode.setName(child.getNodeName());

				Node textChild = node.getFirstChild();
				if (textChild != null) {
					// 当该结点下只有一个文本结点时，效果等同getTextContent()，多个文本节点时根据JDK中对于Node的解释，getNodeValue是取不到值的
					rootNode.setText(textChild.getNodeValue().trim());
				}

				Map<String, String> map = new LinkedHashMap<String, String>();
				// 首先获取当前节点的所有属性节点
				NamedNodeMap nnm = child.getAttributes();
				for (int innerIndex = 0, innerLength = nnm.getLength(); innerIndex < innerLength; innerIndex++) {
					node = nnm.item(innerIndex);
					map.put(node.getNodeName(), node.getNodeValue());
				}
				rootNode.setAttributeMap(map);
			}
		}

		List<NodeVo> childNodes = recursiveElement(doc.getDocumentElement());
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

		NodeList nodeList = element.getChildNodes();
		for (int outIndex = 0, outLength = nodeList.getLength(); outIndex < outLength; outIndex++) {
			Node node = nodeList.item(outIndex);
			// dom会将换行和注释都做为document的节点，排除非注释、非元素节点
			if (node == null || (node.getNodeType() != Node.COMMENT_NODE && node.getNodeType() != Node.ELEMENT_NODE)) {
				continue;
			}

			// xml规范，先写注释，再写节点，所以遇到元素节点，信息就已经完整了
			if (node.getNodeType() == Node.COMMENT_NODE) {
				Comment comment = (Comment) node;
				nodeVo.setComment(comment.getNodeValue());
			} else if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				nodeVo.setName(child.getNodeName());

				Node textChild = node.getFirstChild();
				if (textChild != null) {
					// 当该结点下只有一个文本结点时，效果等同getTextContent()，多个文本节点时根据JDK中对于Node的解释，getNodeValue是取不到值的
					nodeVo.setText(textChild.getNodeValue().trim());
				}

				Map<String, String> map = new LinkedHashMap<String, String>();
				// 首先获取当前节点的所有属性节点
				NamedNodeMap nnm = child.getAttributes();
				for (int innerIndex = 0, innerLength = nnm.getLength(); innerIndex < innerLength; innerIndex++) {
					node = nnm.item(innerIndex);
					map.put(node.getNodeName(), node.getNodeValue());
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
	public static Node selectSingleNode(String express, Object source) {
		return (Node) selectNodeByExpress(express, source, true);
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
	public static NodeList selectNodes(String express, Object source) {
		return (NodeList) selectNodeByExpress(express, source, false);
	}

	private static Object selectNodeByExpress(String express, Object source, boolean single) {
		Object result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			if (single) {
				result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
			} else {
				result = (NodeList) xpath.evaluate(express, source, XPathConstants.NODESET);
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除结点
	 * 
	 * @param doc
	 * @throws Exception
	 */
	public static void deleteNode(Document doc) throws Exception {
		Node node = selectSingleNode("/root/first/first211[@age11='永远18岁11']", doc);
		node.getParentNode().removeChild(node);
		formatToFile(doc, new File("D:/w3c3.xml"));
	}

	public static void main(String[] args) throws Exception {
		InputStream is = DomUtils.class.getResourceAsStream("/com/linzl/cn/xml/xmlRead.xml");

		testCreate();
		is = new FileInputStream("D://w3c.xml");

		Document doc = toDoc(is);
		// NodeVo doc2 = recursiveDoc(doc);
		// Document doc3 = createXML(doc2);
		// formatToFile(doc3, new File("D:/w3c2.xml"));

		deleteNode(doc);
	}

	public static void testCreate() throws Exception {
		NodeVo vo = new NodeVo("root");
		vo.setComment("测试根节点注释");
		List childNodes = new ArrayList();

		NodeVo child = new NodeVo("first");
		child.setComment("第一条测试内容注释");
		List firstChildList = new ArrayList();
		NodeVo child11 = new NodeVo("first211", "内容'好的\"第一条内容211");
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
		Document doc = createXML(vo);
		formatToFile(doc, new File("D:/w3c.xml"));
	}

	public static void findNode() throws Exception {
		InputStream is = new FileInputStream("D://w3c.xml");

		Document doc = toDoc(is);
		// 查找属性时用 英文单词attribute @ 其他不需要
		NodeList node = selectNodes("/root/first/first211[@age11='永远18岁11']", doc);
		System.out.println(node.getLength());
		Node nn = node.item(0);
		System.out.println(nn.getTextContent());
	}

}
