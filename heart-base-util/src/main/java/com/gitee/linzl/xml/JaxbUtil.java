package com.gitee.linzl.xml;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;

/**
 * 使用Jaxb2.0实现XML<->Java Object的Binder.
 * 
 * 特别支持Root对象是List的情形.
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年9月5日
 */
public class JaxbUtil {
	// 多线程安全的Context
	private JAXBContext jaxbContext;

	/**
	 * @param types
	 *            所有需要序列化的Root对象的类型.
	 */
	public JaxbUtil(Class<?>... types) {
		try {
			jaxbContext = JAXBContext.newInstance(types);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Java Object->Xml
	 * 
	 * @param root
	 * @return
	 */
	public String toXml(Object root) {
		return toXml(root, null);
	}

	public void writeToFile(Object root, File file) {
		try {
			createMarshaller(null).marshal(root, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void writeToFile(Collection<?> root, String rootName, File file) {
		CollectionWrapper wrapper = new CollectionWrapper();
		wrapper.collection = root;

		rootName = rootName == null ? "root" : rootName;
		JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(rootName),
				CollectionWrapper.class, wrapper);
		writeToFile(wrapperElement, file);
	}

	/**
	 * Java Object->Xml
	 * 
	 * @param root
	 * @param encoding
	 * @return
	 */
	public String toXml(Object root, String encoding) {
		try {
			StringWriter writer = new StringWriter();
			createMarshaller(encoding).marshal(root, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Java Object->Xml, 特别支持对Root Element是Collection的情形
	 * 
	 * @param root
	 * @param rootName
	 * @param encoding
	 * @return
	 */
	public String toXml(Collection<?> root, String rootName, String encoding) {
		CollectionWrapper wrapper = new CollectionWrapper();
		wrapper.collection = root;

		JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(rootName),
				CollectionWrapper.class, wrapper);

		return toXml(wrapperElement, encoding);
	}

	/**
	 * Xml->Java Object,默认大小写敏感
	 */
	@SuppressWarnings("unchecked")
	public <T> T toBean(String xml) {
		try {
			return (T) createUnmarshaller().unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T toBean(File xml) {
		try {
			return (T) createUnmarshaller().unmarshal(xml);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T toBean(InputStream input) {
		try {
			return (T) createUnmarshaller().unmarshal(input);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建Marshaller
	 */
	private Marshaller createMarshaller(String encoding) {
		try {
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			if (StringUtils.isNotBlank(encoding)) {
				try {
					marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
				} catch (PropertyException e) {
					e.printStackTrace();
				}
			}
			return marshaller;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建UnMarshaller.
	 */
	private Unmarshaller createUnmarshaller() {
		try {
			return jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 封装Root Element 是 Collection的情况.
	 */
	public static class CollectionWrapper {
		@XmlAnyElement
		protected Collection<?> collection;
	}
}
