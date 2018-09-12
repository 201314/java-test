package com.gitee.linzl.xml.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年9月12日
 */
public class CompositeXmlStrategy {
	private List<XmlStrategy> xmlStrategys = null;
	private final Map<String, XmlStrategy> xmlStrategysCache = new ConcurrentHashMap<>(256);

	public CompositeXmlStrategy() {
		xmlStrategys = new ArrayList<>();
		xmlStrategys.add(new Dom4jStrategy());
	}

	public void addXmlStrategy(XmlStrategy xmlStrategy) {
		xmlStrategys.add(xmlStrategy);
	}

	public void clearXmlStrategy() {
		xmlStrategys.clear();
	}

	/**
	 * 获取对应的策略
	 * 
	 * @param xmlType
	 * @return
	 */
	public XmlStrategy getXmlStrategy(String xmlType) {
		XmlStrategy result = xmlStrategysCache.get(xmlType);

		if (result == null) {
			Optional<XmlStrategy> optional = this.xmlStrategys.stream()
					.filter((xmlStrategy) -> xmlStrategy.supports(xmlType)).findFirst();
			if (optional.isPresent()) {
				result = optional.get();
				xmlStrategysCache.put(xmlType, result);
			}
		}
		return result;
	}
}
