package com.gitee.linzl.xml.strategy;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

public class DocumentStrategyFactory {
	public static XmlStrategy read(File file) {
		return new Dom4jStrategy(file);
	}

	public static XmlStrategy read(URL url) {
		return new Dom4jStrategy(url);
	}

	public static XmlStrategy read(InputStream in) {
		return new Dom4jStrategy(in);
	}

	public static XmlStrategy read(Reader reader) {
		return new Dom4jStrategy(reader);
	}

	public static XmlStrategy read(String xmlContent) {
		return new Dom4jStrategy(xmlContent);
	}
}
