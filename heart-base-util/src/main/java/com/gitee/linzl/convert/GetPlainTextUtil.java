package com.gitee.linzl.convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 * apache 读取文档纯文本内容
 * 
 * @author linzl
 * 
 */
public class GetPlainTextUtil {

	public String parseToString(File file) throws IOException, SAXException, TikaException {
		InputStream stream = new FileInputStream(file);
		Tika tika = new Tika();
		try {
			return tika.parseToString(stream);
		} finally {
			stream.close();
		}
	}

	public String parseToPlainText(File file) throws IOException, SAXException, TikaException {
		InputStream stream = new FileInputStream(file);
		Metadata metadata = new Metadata();
		AutoDetectParser parser = new AutoDetectParser();
		try {
			BodyContentHandler handler = new BodyContentHandler();
			parser.parse(stream, handler, metadata);
			return handler.toString();
		} finally {
			stream.close();
		}
	}

	public static void main(String[] args) throws IOException, SAXException, TikaException {
		long start = System.currentTimeMillis();
		File file = new File("D:\\trawe_store\\Cat技术入门总结-0.1.0.doc");
		// 获取到的纯文本内容含有大量的换行，需要进行修改
		String content = new GetPlainTextUtil().parseToPlainText(file);
		System.out.println(content);
		long end = System.currentTimeMillis();
		// parseToStringExample
		System.out.println("时间：" + (end - start));
	}
}
