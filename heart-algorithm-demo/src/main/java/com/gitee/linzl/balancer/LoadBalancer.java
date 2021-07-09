package com.gitee.linzl.balancer;

import java.util.List;

/**
 * 负载均衡算法，一般要伴随健康检查算法一起使用。
 * 健康检查算法的作用就是对所有的服务器进行存活和健康检测，看是否需要提供给负载均衡做选择。
 * 如果一台机器的服务出现了问题，健康检查就会将这台机器从服务列表中去掉，让负载均衡算法看不到这台机器的存在。
 * <p>
 * 具体在加权轮询算法中，当健康检查算法检测出某服务器的状态发生了变化，比如从UP到DOWN或者反之时，就会更新权重并重新计算结果序列。
 *
 * @author linzhenlie-jk
 * @date 2021/7/5
 */
public interface LoadBalancer {
    Object getObject(List<Object> clientsCopy);
}