package com.gitee.linzl.balancer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 加权随机负载
 *
 * @author linzhenlie-jk
 * @date 2021/7/5
 */
public class AliasMethodRandomLoadBalancer implements LoadBalancer {
    /**
     * 服务器与对应的权重
     */
    private AliasMethod aliasMethod;
    /**
     * 服务器与对应的权重,两个List一一对应
     */
    private List<Double> weightList = new LinkedList<>();
    private List<Object> serverList = new LinkedList<>();

    public AliasMethodRandomLoadBalancer(Map<Object, Integer> weights) {
        for (Map.Entry<Object, Integer> entry : weights.entrySet()) {
            if (entry.getValue() <= 0) {
                throw new IllegalArgumentException("Weight can't be less than or equal zero");
            }
            weightList.add(new Double(entry.getValue()));
            serverList.add(entry.getKey());
        }

        aliasMethod = new AliasMethod(weightList, ThreadLocalRandom.current());
    }

    @Override
    public Object getObject(List<Object> clients) {
        int idx = aliasMethod.next();
        return serverList.get(idx);
    }
}
