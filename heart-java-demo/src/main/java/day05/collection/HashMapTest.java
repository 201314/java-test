package day05.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMapTest {
	public static void main(String[] args) {
		Map<Integer,String> map=new HashMap<Integer,String>();
		map.put(1,"Eric");
		map.put(2,"Janet");
		map.put(3,"Jason");
		map.put(4,"Vivi");
		
		map.remove(3);
		
		map.put(4,"Coco");
		Object key;
		Object value;
		Set set=map.keySet();
		Iterator iter=set.iterator();
		while(iter.hasNext()){
				key=iter.next();
				value=map.get(key);
				System.out.println(key+"--->"+value);					
		}
	}
}
