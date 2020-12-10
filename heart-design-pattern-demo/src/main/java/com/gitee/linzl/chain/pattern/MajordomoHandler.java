package com.gitee.linzl.chain.pattern;

//总监
public class MajordomoHandler implements ChainHandler {

    @Override
    public void doFilter(ApplyRequest request, ApplyResponse response, Chain chain) {
        // 处理请假，3~10默认允许
        if (request.getApplyType() == 1 && request.getNumber() < 10) {
            response.setApplyResult("总监通过申请请假");
            return;
        }
        // 处理加薪，500~1000内默认允许
        if (request.getApplyType() == 2 && request.getNumber() < 1000) {
            response.setApplyResult("总监通过申请加薪");
            return;
        }
        chain.doFilter(request, response);
    }
}
