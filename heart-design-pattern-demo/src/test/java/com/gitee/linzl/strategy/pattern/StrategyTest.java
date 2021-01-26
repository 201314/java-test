package com.gitee.linzl.strategy.pattern;

/**
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-8 下午10:33:17
 * 策略模式：定义了一个算法家族，分别封装起来，让它们之间可 以相互替换，此模式让算法的变化，不会影响到使用算法的客户
 * <p>
 * 优点：简化了单元测试，因为每个算法都有自己的实现类，可以通过自己的接口单独测试
 * <p>
 * 用途：就是用来封装算法的，在实践中几乎可以封装任何类型的规则， 只要在分析过程中听到：需要在不同时间使用不同的业务规则，就可
 * 考虑使用策略模式。
 */
public class StrategyTest {
    public static void main(String[] args) {
        Double result;
        Double count = 5d;// 购买数量

        // 第一种 无任何优惠
        SimpleFactory context = new SimpleFactory(0);
        result = context.apply(count * 189);
        System.out.println("无任何优惠 总价格：" + result);

        // 第二种 打折优惠
        context = new SimpleFactory(1);
        result = context.apply(count * 189);
        System.out.println("打0.8折后 总价格：" + result);

        // 第三种 满100返10
        context = new SimpleFactory(2);
        result = context.apply(count * 189);
        System.out.println("满100返10 总价格：" + result);

        // ============更常用的一种获取策略方式
        StrategyFactory factory = StrategyFactory.getInstance();
        // 第一种 无任何优惠
        result = factory.getStrategy(0).apply(count * 189);
        System.out.println("无任何优惠 总价格：" + result);

        // 第二种 打折优惠
        result = factory.getStrategy(1).apply(count * 189);
        System.out.println("打0.8折后 总价格：" + result);

        // 第三种 满100返10
        result = factory.getStrategy(2).apply(count * 189);
        System.out.println("满100返10 总价格：" + result);
    }
}
