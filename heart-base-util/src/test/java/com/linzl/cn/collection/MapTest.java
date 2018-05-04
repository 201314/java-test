package com.linzl.cn.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.linzl.cn.collection.MapUtil;

/**
 * 将map中的空值键值对移除,如key或value为Null、长度为0 则被移除
 * 
 * @author linzl
 * 
 */
public class MapTest {

	@Test
	public void removeNull() {
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		map.put(1, "第一个值是数字");
		map.put("2", "第2个值是字符串");
		map.put(new String[] { "1", "2" }, "第3个值是数组");
		map.put(new ArrayList<Object>(), "第4个值是List");
		map.put(new HashMap<Object, Object>(), "Map 无值");
		map.put("5", "第5个");
		map.put("6", null);
		map.put("7", "");
		map.put("8", "  ");
		map.put(" ", "中文好好哦");
		map.put(null, "好的");
		System.out.println("原map内容");
		System.out.println(map);
		MapUtil.removeNullKey(map);
		System.out.println("移除空key");
		System.out.println(map);
		MapUtil.removeNullValue(map);
		System.out.println("移除空value");
		System.out.println(map);
	}
}
