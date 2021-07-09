package com.gitee.linzl.balancer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 加权随机负载
 *
 * @author linzhenlie-jk
 * @date 2021/7/5
 */
public class RandomLoadBalancer implements LoadBalancer {
    private static final Random RANDOM = ThreadLocalRandom.current();

    /**
     * 服务器与对应的权重
     */
    private final Map<Object, Integer> weights = new ConcurrentHashMap<>();
    private final int defaultWeight;

    public RandomLoadBalancer(Map<Object, Integer> weights, int defaultWeight) {
        for (Map.Entry<Object, Integer> entry : weights.entrySet()) {
            if (entry.getValue() <= 0) {
                throw new IllegalArgumentException("Weight can't be less than or equal zero");
            }
            this.weights.put(entry.getKey(), entry.getValue());
        }
        if (defaultWeight <= 0) {
            throw new IllegalArgumentException("Weight can't be less than or equal zero");
        }
        this.defaultWeight = defaultWeight;
    }

    private Set<Object> getAddresses(List<Object> clients) {
        Set<Object> result = new HashSet<>();
        for (Object entry : clients) {
            result.add(entry);
        }
        return result;
    }

    @Override
    public Object getObject(List<Object> clients) {
        Set<Object> addresses = getAddresses(clients);

        /**
         * 如果不在 weights 范围内，则使用默认权重
         */
        if (!addresses.equals(weights.keySet())) {
            Set<Object> newSet = new HashSet<>(addresses);
            newSet.removeAll(weights.keySet());
            for (Object addr : newSet) {
                weights.put(addr, defaultWeight);
            }
        }

        return getLinearSearch();
    }

    /**
     * 线性扫描: 实现按权重随机的最简单方法，
     * 1.先计算出所有服务器 的权重总和S，
     * 2.调用随机函数得到一个区间在[0, S)的随机值，
     * 3.从头向后扫描服务器列表，并不断从S减掉每个服务器的权重值，当S小于某个道具的权重值时，这个服务器就是选中的那一个
     * 或
     * 先计算出总和列表，列表中每个值是前面N个权重值的总和，这样这个列表就是有序的，再对这个列表使用【线性扫描】得到权重索引
     *
     * @return
     */
    public Object getLinearSearch() {
        /**
         * 总权重
         */
        int weightSum = 0;
        Set<Object> set = weights.keySet();
        for (Object obj : set) {
            int weight = weights.get(obj);
            weightSum += weight;
        }

        int random = RANDOM.nextInt(weightSum);
        /**
         * 线性扫描
         */
        for (Object obj : set) {
            int weight = weights.get(obj);
            random -= weight;
            if (random < 0) {
                return obj;
            }
        }

        Set<Object> client = weights.keySet();
        List<Object> list = new ArrayList<>(client);
        int ind = RANDOM.nextInt(client.size());
        return list.get(ind);
    }

    public Object getLinearSearch2() {
        int weightSum = 0;
        /**
         * 列表中每个值是前面N个权重值的总和,权重和只会越来越大
         * 权重 《==》 服务器
         */
        Map<Integer, Object> map = new TreeMap<>();
        for (Map.Entry<Object, Integer> entry : weights.entrySet()) {
            weightSum += entry.getValue();
            map.put(weightSum, entry.getKey());
        }
        Integer random = RANDOM.nextInt(weightSum);
        return map.entrySet().stream().filter(entry -> random - entry.getKey() < 0).findFirst().get();
    }


    /**
     * 优化方法是用二叉查找，
     * 先计算出总和列表，列表中每个值是前面N个权重值的总和，这样这个列表就是有序的，再对这个列表使用二叉查找得到权重索引
     *
     * @return
     */
    public Object getBinarySearch() {
        int weightSum = 0;
        /**
         * 列表中每个值是前面N个权重值的总和,权重和只会越来越大
         */
        Map<Integer, Object> map = new TreeMap<>();
        Integer[] weightArr = new Integer[map.size()];
        int index = 0;
        for (Map.Entry<Object, Integer> entry : weights.entrySet()) {
            weightSum += entry.getValue();
            weightArr[index++] = weightSum;
            map.put(weightSum, entry.getKey());
        }

        Integer random = RANDOM.nextInt(weightSum);
        Integer key = Arrays.binarySearch(weightArr, random);
        return map.get(weightArr[key]);
    }

}
