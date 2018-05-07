package com.gitee.linzl.xml;

import java.io.File;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 详细方法可见JDK1.6的API SAX采用的事件模型 SAX中一个重要的特点就是它的流式处理，在遇到一个标签的时候，它并不会纪录下以前所碰到的标签，
 * 也就是说，在startElement()方法中，所有你所知道的信息，就是标签的名字和属性，
 * 至于标签的嵌套结构，上层标签的名字，是否有子元属等等其它与结构相关的信息，都是不得而知的 优点：事件驱动模式，对内存消耗小，适用于处理xml中的数据
 * 缺点：不易编码，很难访问同一个xml的多处不同数据，单纯从解析数据来看，sax最好
 * 
 * @author linzl 最后修改时间：2014年10月18日
 */
public class SaxUtils extends DefaultHandler {
	private String currentElement = "";
	private StringBuffer currentValue = new StringBuffer();

	// 开始xml文档分析
	public void startDocument() throws SAXException {
		System.out.println("------文档分析开始--------");
	}

	/**
	 * uri就是命名空间的uri，localName是命名空间的本地名称， qName是结点名称， 而atts是这个结点所包含的属性列表
	 */
	// 开始文档元素分析
	public void startElement(String uri, String localName, String qName, Attributes attrs) {
		currentElement = qName;
		if (attrs != null) {
			int attrLength = attrs.getLength();
			for (int index = 0; index < attrLength; index++) {
				System.out.println("属性名称：" + attrs.getQName(index) + "-->属性值：" + attrs.getValue(index));
			}
		}

	}

	/**
	 * 分析具体文档元素 Sax和Dom一样会解析出空白结点
	 */
	public void characters(char ch[], int start, int length) throws SAXException {
		String value = new String(ch, start, length);
		if ("".equals(value = value.trim())) {
			return;
		}
		// 当前结点值
		currentValue.append(value);
	}

	// 结束成对的元素分析
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (currentElement.equals(qName)) {
			// 取完内容后，重新初始化一个buffer
			currentValue = new StringBuffer();
		}
	}

	// 结束xml文档分析
	public void endDocument() throws SAXException {
		System.out.println("------文档分析结束--------");
	}

	public static void createDocXML() throws TransformerConfigurationException, SAXException {
		// 和dom的区别在前两行
		SAXTransformerFactory sff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		TransformerHandler handler = sff.newTransformerHandler();
		// 设置xml格式必须在前面，否则不生效
		Transformer tf = handler.getTransformer();
		// 换行
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		// 设置xml格式必须在前面，否则不生效

		File file = new File("D://sax.xml");
		Result result = new StreamResult(file);
		handler.setResult(result);

		handler.startDocument();
		AttributesImpl atts = new AttributesImpl();
		String text = "";// 文本内容
		String comment = "";// 注释

		// 创建根据结点，无属性值
		handler.startElement("", "", "root", atts);
		comment = "根结点注释";
		handler.comment(comment.toCharArray(), 0, comment.length());

		atts.clear();
		atts.addAttribute("", "", "age", "", "年龄");
		atts.addAttribute("", "", "height", "", "身高");
		atts.addAttribute("", "", "name", "", "姓名");
		handler.startElement("", "", "rootChildA", atts);
		text = "rootChildA孩子结点";
		handler.characters(text.toCharArray(), 0, text.length());
		handler.endElement("", "", "rootChildA");

		atts.clear();
		handler.startElement("", "", "rootChildB", atts);
		handler.startCDATA();
		text = "rootChildB孩子结点";
		handler.characters(text.toCharArray(), 0, text.length());
		handler.endCDATA();
		handler.endElement("", "", "rootChildB");

		handler.endElement("", "", "root");
		handler.endDocument();
	}

	public static void main(String args[]) {
		// try {
		// createDocXML();
		// } catch (TransformerConfigurationException e1) {
		// e1.printStackTrace();
		// } catch (SAXException e1) {
		// e1.printStackTrace();
		// }

		long lasting = System.currentTimeMillis();
		try {
			URL url = SaxUtils.class.getClassLoader().getResource("com/linzl/cn/xml/xmlRead.xml");

			SAXParserFactory sf = SAXParserFactory.newInstance();
			SAXParser sp = sf.newSAXParser();
			sp.parse(new InputSource(url.getPath()), new SaxUtils());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("运行时间：" + (System.currentTimeMillis() - lasting) + "毫秒");
	}

}