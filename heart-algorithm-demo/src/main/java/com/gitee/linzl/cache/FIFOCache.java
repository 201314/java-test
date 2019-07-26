package com.gitee.linzl.cache;

/**
 * 利用队列实现。FIFO与LRU的区别是FIFO遇到内存中存在的页面不需要调换页面顺序。
 * <p>
 * 置换规则：
 * <p>
 * 1. 淘汰队尾
 * <p>
 * 2. 即将访问的页调入队头
 * <p>
 * 3. 访问存在的页面不用调到队头
 * <p>
 * 
 * @description 先进先出
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年7月15日
 */
public class FIFOCache {

}
