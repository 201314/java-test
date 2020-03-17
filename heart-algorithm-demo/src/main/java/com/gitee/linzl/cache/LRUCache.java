package com.gitee.linzl.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 使用堆栈实现，每次访问都调整堆栈中页面顺序。把被访问页面从栈移出再压入栈顶。
 * 
 * 置换规则：
 * <p>
 * 1.栈顶始终为最新访问过的页面;
 * <p>
 * 2.栈底始终为最近最久未被访问的页面;
 * <p>
 * 3.访问存在的页面要调到栈顶
 * 
 * 
 * 
 * @description 最近最少使用
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年7月15日
 */
public class LRUCache<K, V> implements Cache<K, V> {
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private final Map<K,V> cache ;

	public LRUCache(int maxCapacity) {
		cache = new LinkedHashMap<K,V>(16, DEFAULT_LOAD_FACTOR, true) {
			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				return size() > maxCapacity;
			}
		};
	}

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public boolean remove(K key) {
        return cache.remove(key) != null;
    }

    @Override
    public long size() {
        return cache.size();
    }
}
