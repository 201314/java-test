package com.gitee.linzl.xml.strategy;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import com.gitee.linzl.xml.NodeVo;

public interface XmlStrategy {
	/**
	 * 是否支持该xml策略
	 * 
	 * @return
	 */
	public boolean supports(String xmlParseType);

	public XmlStrategy read(File file);

	public XmlStrategy read(URL url);

	public XmlStrategy read(InputStream is);

	public XmlStrategy read(Reader reader);

	/**
	 * xml内容转字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	public String readAsText() throws Exception;

	/**
	 * xml内容转字符串
	 * 
	 * @param format
	 *            是否格式化
	 * @return
	 * @throws Exception
	 */
	public String readAsText(boolean format) throws Exception;

	/**
	 * xml输出到文件
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void write(File file) throws Exception;

	/**
	 * xml输出到文件
	 * 
	 * @param file
	 * @param format
	 *            是否格式化输出到文件
	 * @throws Exception
	 */
	public void write(File file, boolean format) throws Exception;

	/**
	 * 给根结点添加孩子结点
	 * 
	 * @param nodes
	 * @throws Exception
	 */
	public void appendToRoot(NodeVo node) throws Exception;

	public void appendToRoot(List<NodeVo> nodes) throws Exception;

	/**
	 * 向当前文档中的搜索到的节点表达式添加孩子节点
	 * 
	 * @param express
	 *            所查节点的表达式
	 * @param node
	 * @throws Exception
	 */
	public void appendToNode(String express, NodeVo node) throws Exception;

	/**
	 * 向当前文档中的搜索到的节点表达式添加孩子节点
	 * 
	 * @param express
	 *            所查节点的表达式
	 * @param nodes
	 * @throws Exception
	 */
	public void appendToNode(String express, List<NodeVo> nodes) throws Exception;

	/**
	 * 从根结点开始递归出所有节点信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public NodeVo recursiveFromRoot() throws Exception;

}
