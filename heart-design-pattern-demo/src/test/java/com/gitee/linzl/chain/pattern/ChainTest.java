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
        List<FilterChainHandler> handler = Arrays.asList(

                new ProjectChainHandler(),

                new MajordomoChainHandler(),

                new GeneralChainHandler());

        ChainRequest request = new ChainRequest();
        request.setApplyType(1);
        request.setNumber(5);

        ChainResponse response = new ChainResponse();
        FilterChain chain = new FilterChain(handler);
        chain.doFilter(request,response);
        System.out.println("请假处理结果：" + response.getApplyResult());

        ChainRequest request2 = new ChainRequest();
        request2.setApplyType(2);
        request2.setNumber(1260);
        FilterChain chain2 = new FilterChain(handler);
        ChainResponse response2 = new ChainResponse();
        chain2.doFilter(request2,response2);
        System.out.println("申请加薪处理结果：" + response2.getApplyResult());
    }
}
