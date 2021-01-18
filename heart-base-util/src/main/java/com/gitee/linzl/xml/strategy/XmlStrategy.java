package com.gitee.linzl.xml.strategy;

import com.gitee.linzl.xml.NodeVo;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;

public interface XmlStrategy {
    /**
     * 是否支持该xml策略
     *
     * @param xmlParseType
     * @return
     */
    boolean supports(String xmlParseType);

    XmlStrategy read(File file);

    XmlStrategy read(URL url);

    XmlStrategy read(InputStream is);

    XmlStrategy read(Reader reader);

    /**
     * xml内容转字符串
     *
     * @return
     * @throws Exception
     */
    String readAsText() throws Exception;

    /**
     * xml内容转字符串
     *
     * @param format 是否格式化
     * @return
     * @throws Exception
     */
    String readAsText(boolean format) throws Exception;

    /**
     * xml输出到文件
     *
     * @param file
     * @throws Exception
     */
    void write(File file) throws Exception;

    /**
     * xml输出到文件
     *
     * @param file
     * @param format 是否格式化输出到文件
     * @throws Exception
     */
    void write(File file, boolean format) throws Exception;

    /**
     * 给根结点添加孩子结点
     *
     * @param node
     * @throws Exception
     */
    void appendToRoot(NodeVo node) throws Exception;

    void appendToRoot(List<NodeVo> nodes) throws Exception;

    /**
     * 向当前文档中的搜索到的节点表达式添加孩子节点
     *
     * @param express 所查节点的表达式
     * @param node
     * @throws Exception
     */
    void appendToNode(String express, NodeVo node) throws Exception;

    /**
     * 向当前文档中的搜索到的节点表达式添加孩子节点
     *
     * @param express 所查节点的表达式
     * @param nodes
     * @throws Exception
     */
    void appendToNode(String express, List<NodeVo> nodes) throws Exception;

    /**
     * 从根结点开始递归出所有节点信息
     *
     * @return
     * @throws Exception
     */
    NodeVo recursiveFromRoot() throws Exception;
}
