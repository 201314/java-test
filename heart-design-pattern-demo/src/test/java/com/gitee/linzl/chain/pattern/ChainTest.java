package com.gitee.linzl.chain.pattern;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ChainTest {
    /**
     * 此种责任模式，更能解耦，各责任方不需要知道处理不了时下一个责任方是谁。
     */
    @Test
    public void testDemo2() {
        List<ChainHandler> handler = Arrays.asList(

                new ProjectHandler(),

                new MajordomoHandler(),

                new GeneralHandler());

        Apply apply = new Apply();
        apply.setApplyType(1);
        apply.setNumber(5);

        Chain chain = new Chain(handler, apply);
        System.out.println("请假处理结果：" + chain.proceed());

        apply.setApplyType(2);
        apply.setNumber(126000);
        chain = new Chain(handler, apply);
        System.out.println("申请加薪处理结果：" + chain.proceed());
    }
}
