package com.gitee.linzl.balancer;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 加权轮询算法的原理就是：根据服务器的不同处理能力，如新老服务器混用,给每个服务器分配不同的权值，使其能够接受相应权值数的服务请求。
 * <p>
 * server a weight=5;
 * server b weight=1;
 * server c weight=1;
 * <p>
 * 普通的加权轮询算法：生成的序列是这样的:{a,a, a, a, a, c, b},会有5个连续的请求落在后端a上,分布不太均匀
 * <p>
 * 平滑的加权轮询算法：生成的序列如为{a, a, b, a, c, a, a},a没有被连续选中
 * <p>
 *
 * 假设有 N 台实例 S = {S1, S2, …, Sn}，配置权重 W = {W1, W2, …, Wn}，有效权重 CW = {CW1, CW2, …, CWn}。
 * 每个实例 i 除了存在一个配置权重 Wi 外，还存在一个当前有效权重 CWi，且 CWi 初始化为 Wi；
 * 指示变量 currentPos 表示当前选择的实例 ID，初始化为 -1；
 * 所有实例的配置权重和为 weightSum；
 * <p>
 * 那么，调度算法可以描述为：
 * 1、初始每个实例 i 的 当前有效权重 CWi 为 配置权重 Wi，并求得配置权重和 weightSum；
 * 2、选出 当前有效权重 最大 的实例，将 当前有效权重 CWi 减去所有实例的 权重和 weightSum，且变量 currentPos 指向此位置；
 * 3、将每个实例 i 的 当前有效权重 CWi 都加上 配置权重 Wi；
 * 4、取到变量 currentPos 指向的实例；
 * 5、每次调度重复上述步骤 2、3、4；
 * <p>
 * 证明：https://www.jianshu.com/p/836193df61db
 * https://www.zhihu.com/question/395440923
 *
 * @author linzhenlie-jk
 * @date 2021/7/5
 */
public class WeightedRoundRobinBalancer implements LoadBalancer {

    static class WeightEntry {
        /**
         * 指定的服务器的权重，这个值是固定不变的
         */
        final int weight;
        /**
         * 服务器目前的权重,一开始为0,之后会动态调整
         */
        AtomicInteger currentWeight = new AtomicInteger(0);

        WeightEntry(int weight) {
            this.weight = weight;
            currentWeight.set(0);
        }

        public int getWeight() {
            return weight;
        }

        public int increaseCurrent() {
            return currentWeight.addAndGet(weight);
        }

        public void reduce(int total) {
            currentWeight.addAndGet(-1 * total);
        }
    }

    private final Map<Object, WeightEntry> weights = new ConcurrentHashMap<>();

    private final int defaultWeight;

    public WeightedRoundRobinBalancer(Map<Object, Integer> weights, int defaultWeight) {
        for (Entry<Object, Integer> entry : weights.entrySet()) {
            if (entry.getValue() <= 0) {
                throw new IllegalArgumentException("Weight can't be less than or equal zero");
            }
            this.weights.put(entry.getKey(), new WeightEntry(entry.getValue()));
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
                weights.put(addr, new WeightEntry(defaultWeight));
            }
        }

        // 求得最大当前有效权重的实例
        int maxWeight = Integer.MIN_VALUE;
        Object maxObj = null;
        WeightEntry maxWeightObj = null;

        int weightSum = 0;
        Set<Object> set = weights.keySet();
        for (Object obj : set) {
            WeightEntry weightEntry = weights.get(obj);
            int weight = weightEntry.getWeight();
            int cur = weightEntry.increaseCurrent();
            if (cur > maxWeight) {
                maxWeight = cur;
                maxObj = obj;
                maxWeightObj = weightEntry;
            }
            weightSum += weight;
        }

        // 当前选中最大的权重减去权重和
        maxWeightObj.reduce(weightSum);

        // 调整每个实例的当前有效权重，加上配置权重
        weights.values().stream().forEach(WeightEntry::increaseCurrent);
        return maxObj;
    }
}
