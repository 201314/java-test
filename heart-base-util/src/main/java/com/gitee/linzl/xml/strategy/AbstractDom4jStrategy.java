package com.gitee.linzl.xml.strategy;

import com.gitee.linzl.xml.NodeVo;
import org.dom4j.Node;

import java.util.List;
import java.util.Objects;

public interface AbstractDom4jStrategy extends XmlStrategy {
    /**
     * 向某个Node添加孩子结点
     *
     * @param node   结点类型只能是Element或Document
     * @param nodeVo
     * @throws Exception
     */
    void appendToNode(Node node, NodeVo nodeVo) throws Exception;

    /**
     * 向多个Node添加孩子结点
     *
     * @param nodeList 结点类型只能是Element或Document
     * @param nodeVo
     * @throws Exception
     */
    void appendToNode(List<Node> nodeList, NodeVo nodeVo) throws Exception;

    /**
     * 向某个Node添加孩子结点
     *
     * @param node       结点类型只能是Element或Document
     * @param nodeVoList
     * @throws Exception
     */
    void appendToNode(Node node, List<NodeVo> nodeVoList) throws Exception;

    /**
     * 向多个Node添加孩子结点
     *
     * @param nodeList   结点类型只能是Element或Document
     * @param nodeVoList
     * @throws Exception
     */
    void appendToNode(List<Node> nodeList, List<NodeVo> nodeVoList) throws Exception;

    /**
     * 向某个Node之后添加兄弟结点
     *
     * @param node   结点类型只能是Element或Document
     * @param nodeVo
     * @throws Exception
     */
    void appendAfterNode(Node node, NodeVo nodeVo) throws Exception;

    /**
     * 向某个Node之后添加兄弟结点
     *
     * @param node       结点类型只能是Element或Document
     * @param nodeVoList
     * @throws Exception
     */
    void appendAfterNode(Node node, List<NodeVo> nodeVoList) throws Exception;

    /**
     * 向某个Node之前添加兄弟结点
     *
     * @param node   结点类型只能是Element或Document
     * @param nodeVo
     * @throws Exception
     */
    void appendBeforeNode(Node node, NodeVo nodeVo) throws Exception;

    /**
     * 向某个Node之前添加兄弟结点
     *
     * @param node       结点类型只能是Element或Document
     * @param nodeVoList
     * @throws Exception
     */
    void appendBeforeNode(Node node, List<NodeVo> nodeVoList) throws Exception;

    /**
     * 从某个元素开始递归出所有孩子节点信息
     *
     * @param node 结点类型只能是Element或Document
     * @return
     * @throws Exception
     */
    List<NodeVo> recursiveFromNode(Node node) throws Exception;

    /**
     * 全文查找节点，返回符合条件的第一个节点
     *
     * @param express
     * @return
     * @throws Exception
     */
    Node selectNode(String express) throws Exception;

    /**
     * 全文查找节点，返回符合条件的节点集。
     *
     * @param express
     * @return
     * @throws Exception
     */
    List<Node> selectNodes(String express) throws Exception;

    /**
     * 查找节点，返回符合条件的第一个节点
     *
     * @param express 所查节点的表达式
     * @param node    所查找的资源
     * @return
     */
    Node selectNode(String express, Node node) throws Exception;

    /**
     * 查找节点，返回符合条件的节点集。
     *
     * @param express 所查节点的表达式
     * @param node    所查找的资源
     * @return
     */
    List<Node> selectNodes(String express, Node node) throws Exception;

    default boolean isBranch(Node node) {
        if (Objects.nonNull(node)) {
            if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.DOCUMENT_NODE) {
                return true;
            }
        }
        return false;
    }
}
