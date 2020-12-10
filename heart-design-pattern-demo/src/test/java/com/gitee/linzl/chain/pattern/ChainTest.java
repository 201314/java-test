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

        ApplyRequest request = new ApplyRequest();
        request.setApplyType(1);
        request.setNumber(5);

        ApplyResponse response = new ApplyResponse();
        Chain chain = new Chain(handler);
        chain.doFilter(request,response);
        System.out.println("请假处理结果：" + response.getApplyResult());

        ApplyRequest request2 = new ApplyRequest();
        request2.setApplyType(2);
        request2.setNumber(1260);
        Chain chain2 = new Chain(handler);
        ApplyResponse response2 = new ApplyResponse();
        chain2.doFilter(request2,response2);
        System.out.println("申请加薪处理结果：" + response2.getApplyResult());
    }
}
