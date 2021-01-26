package com.gitee.linzl.chain.pattern;

//总经理
public class GeneralChainHandler implements ChainHandler {

    @Override
    public void doFilter(ChainRequest request, ChainResponse response, Chain chain) {
        // 处理请假，3~15默认允许
        if (request.getApplyType() == 1 && request.getNumber() < 15) {
            response.setApplyResult("总经理通过申请请假");
            return;
        }
        // 处理加薪，500~1500内默认允许
        if (request.getApplyType() == 2 && request.getNumber() < 1500) {
            response.setApplyResult("总经理通过申请加薪");
            return;
        }
        chain.doFilter(request, response);
    }
}
