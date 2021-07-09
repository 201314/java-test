package com.gitee.linzl.balancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询算法
 * <p>
 * 是最简单的一种负载均衡算法。它的原理是把来自用户的请求轮流分配给内部的服务器：从服务器1开始，直到服务器N，然后重新开始循环。
 * <p>
 * 算法的优点是其简洁性，它无需记录当前所有连接的状态，所以它是一种无状态调度。
 * <p>
 * 轮询算法假设所有服务器的处理性能都相同，不关心每台服务器的当前连接数和响应速度。
 * 当请求服务间隔时间变化比较大时，轮询算法容易导致服务器间的负载不平衡。
 * 所以此种均衡算法适合于服务器组中的所有服务器都有相同的软硬件配置并且平均服务请求相对均衡的情况。
 *
 * @author linzhenlie-jk
 * @date 2021/7/5
 */
public class RoundRobinLoadBalancer implements LoadBalancer {
    private final AtomicInteger index = new AtomicInteger(-1);

    @Override
    public Object getObject(List<Object> clientsCopy) {
        int ind = Math.abs(index.incrementAndGet() % clientsCopy.size());
        return clientsCopy.get(ind);
    }

}