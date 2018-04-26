package jdk7.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeDemo {

	public static void main(String[] args) {
		// 7之前
		Map<String, List<String>> oldMap = new HashMap<String, List<String>>();
		// JDK7之后
		Map<String, List<String>> newMap = new HashMap<>();
	}
}
