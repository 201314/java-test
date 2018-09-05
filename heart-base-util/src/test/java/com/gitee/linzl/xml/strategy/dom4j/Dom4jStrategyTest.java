package com.gitee.linzl.xml.strategy.dom4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Branch;
import org.dom4j.Node;
import org.junit.Test;

import com.gitee.linzl.xml.NodeVo;
import com.gitee.linzl.xml.strategy.DocumentStrategyFactory;
import com.gitee.linzl.xml.strategy.Dom4jStrategy;
import com.gitee.linzl.xml.strategy.XmlStrategy;

public class Dom4jStrategyTest {
	@Test
	public void readAsText() {
		InputStream is = XmlStrategy.class.getClassLoader().getResourceAsStream("com/gitee/linzl/xml/xmlRead.xml");
		Dom4jStrategy strategy = (Dom4jStrategy) DocumentStrategyFactory.read(is);
		System.out.println("没有格式化：" + strategy.readAsText());
		System.out.println("==格式化==：" + strategy.readAsText(true));
	}

	@Test
	public void write() throws Exception {
		InputStream is = XmlStrategy.class.getClassLoader().getResourceAsStream("com/gitee/linzl/xml/xmlRead.xml");
		Dom4jStrategy strategy = (Dom4jStrategy) DocumentStrategyFactory.read(is);
		strategy.write(new File("D:/dom4j2.xml"));
		strategy.write(new File("D://dom4j2-format.xml"), true);
	}

	@Test
	public void appendToRoot() throws IOException {
		NodeVo vo = new NodeVo("root");
		vo.setComment("测试根节点注释");
		List<NodeVo> childNodes = new ArrayList<>();

		NodeVo child = new NodeVo("first");
		child.setComment("第一条测试内容注释");
		List<NodeVo> firstChildList = new ArrayList<>();
		NodeVo child11 = new NodeVo("first211", "33'好的第一条内容21122");
		child11.setComment("第一条测试内容注释211");
		Map<String, String> attr11 = new LinkedHashMap<String, String>();
		attr11.put("age11", "永远18岁11");
		attr11.put("title11", "提示信息11");
		child11.setAttributeMap(attr11);

		NodeVo child12 = new NodeVo("first212", "第一条内容212");
		child12.setComment("第一条测试内容注释212");
		Map<String, String> attr12 = new LinkedHashMap<String, String>();
		attr12.put("age12", "永远18岁12");
		attr12.put("title12", "提示信息12");
		child12.setAttributeMap(attr12);

		firstChildList.add(child11);
		firstChildList.add(child12);
		child.setChildNodes(firstChildList);

		childNodes.add(child);

		NodeVo child2 = new NodeVo("first2", "第一条内容2");
		child2.setComment("第一条测试内容注释2");
		Map<String, String> attr = new LinkedHashMap<String, String>();
		attr.put("age", "永远18岁");
		attr.put("title", "提示信息");
		child2.setAttributeMap(attr);
		childNodes.add(child2);

		vo.setChildNodes(childNodes);

		Dom4jStrategy strategy = new Dom4jStrategy();
		try {
			strategy.appendToRoot(vo);
			strategy.write(new File("D://create.xml"), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void appendListToRoot() {
		Dom4jStrategy strategy = new Dom4jStrategy();
		List<NodeVo> list = new ArrayList<>();
		NodeVo child = new NodeVo("node1");
		child.setComment("第一条测试内容注释==1");
		child.setText("我是子节点");
		list.add(child);
		NodeVo child2 = new NodeVo("node2");
		list.add(child2);
		try {
			strategy.appendToRoot(list);
			strategy.write(new File("D://list.xml"), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void appendToAssignNode() {

	}

	@Test
	public void appendListToAssignNode() {
		Dom4jStrategy strategy = new Dom4jStrategy(new File("D://create.xml"));

		NodeVo child = new NodeVo("first==1");
		child.setComment("第一条测试内容注释==1");
		List<NodeVo> firstChildList = new ArrayList<>();
		NodeVo child11 = new NodeVo("first211==1", "33'好的第一条内容21122==1");
		child11.setComment("第一条测试内容注释211==1");
		Map<String, String> attr11 = new LinkedHashMap<String, String>();
		attr11.put("age11==1", "永远18岁11");
		attr11.put("title11==1", "提示信息11");
		child11.setAttributeMap(attr11);

		NodeVo child12 = new NodeVo("first212==1", "第一条内容212");
		child12.setComment("第一条测试内容注释212==1");
		Map<String, String> attr12 = new LinkedHashMap<String, String>();
		attr12.put("age12==1", "永远18岁12");
		attr12.put("title12==1", "提示信息12");
		child12.setAttributeMap(attr12);

		firstChildList.add(child11);
		firstChildList.add(child12);
		child.setChildNodes(firstChildList);

		List<NodeVo> list = new ArrayList<>();
		list.add(child);
		try {
			Node node = strategy.selectNode("/root/first/first211[@age11='永远18岁11']", strategy.getDoc());
			strategy.appendToNode((Branch) node, list);
			strategy.write(new File("D://hello.xml"), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void appendAfterNode() throws Exception {
		Dom4jStrategy strategy = new Dom4jStrategy(new File("D://hello.xml"));
		Node node = strategy.selectNode("/root/first/first211[@age11='永远18岁11']", strategy.getDoc());
		NodeVo vo = new NodeVo();
		vo.setComment("我是add注释");
		vo.setName("add");
		vo.setText("我在first211之后");

		List<NodeVo> childNodes = new ArrayList<>();
		NodeVo child = new NodeVo("node1");
		child.setComment("我是node1注释");
		child.setText("我是node1");
		childNodes.add(child);
		NodeVo child2 = new NodeVo("node2");
		child2.setText("我是node2");
		childNodes.add(child2);

		vo.setChildNodes(childNodes);
		strategy.appendAfterNode((Branch) node, vo);
		strategy.write(new File("D://appendAfterNode.xml"), true);
	}

	@Test
	public void appendListAfterNode() throws Exception {
		Dom4jStrategy strategy = new Dom4jStrategy(new File("D://hello.xml"));
		Node node = strategy.selectNode("/root/first/first211[@age11='永远18岁11']", strategy.getDoc());
		NodeVo vo = new NodeVo();
		vo.setComment("我是add注释");
		vo.setName("add");
		vo.setText("我在first211之后");

		List<NodeVo> childNodes = new ArrayList<>();
		NodeVo child = new NodeVo("node1");
		child.setComment("我是node1注释");
		child.setText("我是node1");
		childNodes.add(child);
		NodeVo child2 = new NodeVo("node2");
		child2.setText("我是node2");
		childNodes.add(child2);

		vo.setChildNodes(childNodes);
		strategy.appendAfterNode((Branch) node, childNodes);
		strategy.write(new File("D://appendListAfterNode.xml"), true);
	}

	@Test
	public void appendBeforeNode() throws Exception {
		Dom4jStrategy strategy = new Dom4jStrategy(new File("D://hello.xml"));
		Node node = strategy.selectNode("/root/first/first211[@age11='永远18岁11']", strategy.getDoc());
		NodeVo vo = new NodeVo();
		vo.setComment("我是add注释");
		vo.setName("add");
		vo.setText("我在first211之后");

		List<NodeVo> childNodes = new ArrayList<>();
		NodeVo child = new NodeVo("node1");
		child.setComment("我是node1注释");
		child.setText("我是node1");
		childNodes.add(child);
		NodeVo child2 = new NodeVo("node2");
		child2.setText("我是node2");
		childNodes.add(child2);

		vo.setChildNodes(childNodes);
		strategy.appendBeforeNode((Branch) node, vo);
		strategy.write(new File("D://appendBeforeNode.xml"), true);
	}

	@Test
	public void recursiveFromRoot() throws Exception {
		Dom4jStrategy strategy = new Dom4jStrategy(new File("D://hello.xml"));
		strategy.recursiveFromRoot();
	}

	@Test
	public void recursiveFromElement() throws Exception {
		Dom4jStrategy strategy = new Dom4jStrategy(new File("D://qwe.xml"));
		strategy.write(new File("D://jaxb2.xml"), true);
	}

	@Test
	public void selectNode() {

	}

	@Test
	public void selectNodes() throws Exception {
	}

	@Test
	public void deleteNode() throws Exception {
	}
}
