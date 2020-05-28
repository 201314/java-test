package com.gitee.linzl.xml.strategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXEventRecorder;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXWriter;

import com.gitee.linzl.xml.SaxUtils;

/**
 * 回放xml解析过程
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年9月4日
 */
public class Dom4jStrategy2 {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(new File("D://create.xml"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		doc.normalize();

		// 回放xml解析过程
		SAXEventRecorder recorder = new SAXEventRecorder();
		SAXWriter saxWriter = new SAXWriter(recorder, recorder);
		saxWriter.write(doc);

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("D://111.txt")));
		oos.writeObject(recorder);

		read();
	}

	@SuppressWarnings("resource")
	public static void read() throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("D://111.txt")));
		SAXEventRecorder recorder = (SAXEventRecorder) ois.readObject();
		SaxUtils saxContentHandler = new SaxUtils();
		recorder.replay(saxContentHandler);
	}
}
