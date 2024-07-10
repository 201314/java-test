package com.gitee.linzl.lottery;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author linzhenlie-jk
 * @date 2024/4/8
 */
public class PrizeMathRandomTest {
    @Test
    public void randomTest() {
        PrizeMathRandom tool = new PrizeMathRandom();
        List<Prize> prizes = getPrizes();

        System.out.println("抽奖开始");
        tool.computeRatio(prizes);

        Map<String, AtomicInteger> map = new HashMap<>();
        // 打印100个测试概率的准确性
        for (int i = 0; i < 10000; i++) {
            Prize prize = tool.getPrizeIndex();
            System.out.println("第" + i + "次抽中的奖品为：" + prize.getPrizeName());
            System.out.println("--------------------------------");
            map.computeIfAbsent(prize.getPrizeName(), key -> new AtomicInteger(0)).incrementAndGet();
        }
        System.out.println("抽奖结束");
        System.out.println("每种奖品抽到的数量为：" + map);
    }

    private static List<Prize> getPrizes() {
        List<Prize> prizes = new ArrayList<Prize>();

        Prize p1 = new Prize();
        p1.setPrizeName("范冰冰海报");
        p1.setPrizeWeight(1);// 奖品的权重设置成1
        prizes.add(p1);

        Prize p2 = new Prize();
        p2.setPrizeName("上海紫园1号别墅");
        p2.setPrizeWeight(2);// 奖品的权重设置成2
        prizes.add(p2);

        Prize p3 = new Prize();
        p3.setPrizeName("奥迪a9");
        p3.setPrizeWeight(3);// 奖品的权重设置成3
        prizes.add(p3);

        Prize p4 = new Prize();
        p4.setPrizeName("双色球彩票");
        p4.setPrizeWeight(4);// 奖品的权重设置成4
        prizes.add(p4);
        return prizes;
    }
}
