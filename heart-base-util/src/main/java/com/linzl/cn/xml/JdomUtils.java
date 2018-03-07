package com.linzl.cn.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.JDOMSource;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.xml.sax.InputSource;

/**
 * 依赖 jdom-2.0.5.jar
 * 
 * @author linzl 最后修改时间：2014年10月10日
 */
public class JdomUtils {
	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 使用JDK自带的类转换Document对象转成字符串
	 * 
	 * @param doc
	 * @return
	 * @throws TransformerException
	 */
	public static String docToString(Document doc) throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty("encoding", DEFAULT_CHARSET);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		transformer.transform(new JDOMSource(doc), new StreamResult(bos));
		return bos.toString();
	}

	/**
	 * xml文件转Document
	 * 
	 * @param file
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Document toDoc(File file) throws JDOMException, IOException {
		SAXBuilder sax = new SAXBuilder();
		Document doc = sax.build(file);
		return doc;
	}

	/**
	 * xml文件转Document
	 * 
	 * @param file
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Document toDoc(InputStream is) throws JDOMException, IOException {
		SAXBuilder sax = new SAXBuilder();
		Document doc = sax.build(is);
		return doc;
	}

	/**
	 * xml文件转Document
	 * 
	 * @param file
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Document toDoc(String pathName, String charset) throws JDOMException, IOException {
		SAXBuilder sax = new SAXBuilder();
		InputStream is = new FileInputStream(pathName);
		charset = (charset == null || charset.trim().length() <= 0) ? DEFAULT_CHARSET : charset;
		InputStreamReader isr = new InputStreamReader(is, charset);
		Document doc = sax.build(isr);
		return doc;
	}

	/**
	 * 字符串内容转换成Document对象
	 * 
	 * @param xmlStr
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Document stringToDoc(String xmlStr) throws JDOMException, IOException {
		StringReader sr = new StringReader(xmlStr);
		InputSource is = new InputSource(sr);
		Document doc = (new SAXBuilder()).build(is);
		return doc;
	}

	/**
	 * Document对象格式化成字符串
	 * 
	 * @param doc
	 * @return
	 * @throws IOException
	 */
	public static String formatToString(Document doc) throws IOException {
		Format format = Format.getPrettyFormat();
		format.setEncoding(DEFAULT_CHARSET);// 设置xml文件的字符为UTF-8，解决中文问题

		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		XMLOutputter xmlout = new XMLOutputter(format);
		xmlout.output(doc, bo);
		return bo.toString();
	}

	/**
	 * 将Document对象格式化到目标文件
	 * 
	 * @param doc
	 * @param targetFile
	 * @throws IOException
	 */
	public static void formatToFile(Document doc, File targetFile) throws IOException {
		Format format = Format.getPrettyFormat();
		// format.setEncoding(DEFAULT_CHARSET);// 设置xml文件的字符为UTF-8，解决中文问题

		OutputStream os = new FileOutputStream(targetFile);
		XMLOutputter xmlout = new XMLOutputter(format);
		xmlout.output(doc, os);
	}

	/**
	 * 将xmlStr格式化输出到目标文件
	 * 
	 * @param xmlStr
	 * @param targetFile
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static void formatToFile(String xmlStr, File targetFile) throws JDOMException, IOException {
		Document doc = stringToDoc(xmlStr);
		formatToFile(doc, targetFile);
	}

	/**
	 * 将xml文件格式化输出到目标文件
	 * 
	 * @param srcFile
	 *            xml源文件
	 * @param targetFile
	 *            格式化输出xml文件
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static void formatToFile(File srcFile, File targetFile) throws JDOMException, IOException {
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
		Document doc = new Document();
		// 节点注释, xml规范，注释在前
		if (vo.getComment() != null) {
			Comment comment = new Comment(vo.getComment());
			doc.addContent(comment);
		}
		Element root = new Element(vo.getName());
		addChildsToNode(root, vo.getChildNodes());
		doc.addContent(root);
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
				Comment comment = new Comment(nodeVo.getComment());
				element.addContent(comment);
			}
			// 节点名称
			Element childElement = new Element(nodeVo.getName());
			if (nodeVo.getText() != null) {
				// 节点文本内容
				childElement.addContent(nodeVo.getText());
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
			element.addContent(childElement);
			addChildsToNode(childElement, nodeVo.getChildNodes());
		}
	}

	/**
	 * 从根结点开始递归出所有节点信息
	 * 
	 * @param doc
	 */
	public static NodeVo recursiveDoc(Document doc) {
		List<Content> nodeList = doc.getContent();
		NodeVo rootNode = new NodeVo();
		for (Content content : nodeList) {
			// dom会将换行和注释都做为document的节点，排除非注释、非元素节点
			if (content == null || (content.getCType() != CType.Comment && content.getCType() != CType.Element)) {
				continue;
			}
			// xml规范，先写注释，再写节点，所以遇到元素节点，信息就已经完整了
			if (content.getCType() == CType.Comment) {
				Comment comment = (Comment) content;
				rootNode.setComment(comment.getText());
			} else if (content.getCType() == CType.Element) {
				Element child = (Element) content;
				rootNode.setName(child.getName());
				rootNode.setText(child.getTextTrim());

				Map<String, String> map = new LinkedHashMap<String, String>();
				// 遍历属性节点
				for (Attribute attr : child.getAttributes()) {
					map.put(attr.getName(), attr.getValue());
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

		List<Content> nodeList = element.getContent();
		NodeVo nodeVo = new NodeVo();
		for (Content content : nodeList) {
			// dom会将换行和注释都做为document的节点，排除非注释、非元素节点
			if (content == null || (content.getCType() != CType.Comment && content.getCType() != CType.Element)) {
				continue;
			}
			// xml规范，先写注释，再写节点，所以遇到元素节点，信息就已经完整了
			if (content.getCType() == CType.Comment) {
				Comment comment = (Comment) content;
				nodeVo.setComment(comment.getText());
			} else if (content.getCType() == CType.Element) {
				Element child = (Element) content;
				nodeVo.setName(child.getName());
				nodeVo.setText(child.getTextTrim());

				Map<String, String> map = new LinkedHashMap<String, String>();
				// 遍历属性节点
				for (Attribute attr : child.getAttributes()) {
					map.put(attr.getName(), attr.getValue());
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
	public static Element selectSingleElement(String express, Object node) {
		XPathFactory factory = XPathFactory.instance();
		XPathExpression expression = factory.compile(express);
		return (Element) expression.evaluateFirst(node);
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
	public static List selectElements(String express, Object node) {
		XPathFactory factory = XPathFactory.instance();
		XPathExpression<Object> expression = factory.compile(express);
		return expression.evaluate(node);
	}

	public static void deleteNode(Document doc) throws Exception {
		Element node = selectSingleElement("/root/first/first211[@age11='永远18岁11']", doc);
		node.getParent().removeContent(node);
		formatToFile(doc, new File("D:/dom4j3.xml"));
	}

	public static void main(String[] args) throws Exception {
		InputStream is = JdomUtils.class.getResourceAsStream("/com/linzl/cn/xml/xmlRead.xml");

		is = JdomUtils.class.getClassLoader().getResourceAsStream("com/linzl/cn/xml/xmlRead.xml");

		testCreate();
		is = new FileInputStream("D://jdom.xml");
		Document doc = toDoc(is);
		// NodeVo doc2 = recursiveDoc(doc);
		// Document doc3 = createXML(doc2);
		// formatToFile(doc3, new File("D:/jdom2.xml"));

		// findNode();
		deleteNode(doc);
	}

	public static void testCreate() throws Exception {
		NodeVo vo = new NodeVo("root");
		vo.setComment("测试根节点注释");
		List childNodes = new ArrayList();

		NodeVo child = new NodeVo("first");
		child.setComment("第一条测试内容注释");
		List firstChildList = new ArrayList();
		NodeVo child11 = new NodeVo("first211", "第一条内容211");
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
		formatToFile(doc, new File("D:/jdom.xml"));
	}

	public static void findNode() throws Exception {
		InputStream is = new FileInputStream("D://jdom.xml");

		Document doc = toDoc(is);
		// 查找属性时用 英文单词attribute @ 其他不需要
		List node = selectElements("/root/first/first211[@age11='永远18岁11']", doc);
		for (int i = 0; i < node.size(); i++) {
			Element element = (Element) node.get(i);
			System.err.println(element.getTextTrim());
		}

	}
}
