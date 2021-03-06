package com.gitee.linzl.chain.pattern;

//项目经理
public class ProjectChainHandler implements FilterChainHandler {

    // 处理申请，默认为申请不通过
    @Override
    public void doFilter(ChainRequest request, ChainResponse response, FilterChain chain) {
        // 处理请假
        if (request.getApplyType() == 1 && request.getNumber() <= 3) {
            response.setApplyResult("项目经理通过申请请假");
            return;
        }
        // 处理加薪
        if (request.getApplyType() == 2 && request.getNumber() <= 500) {
            response.setApplyResult("项目经理通过申请加薪");
            return;
        }
        chain.doFilter(request, response);
    }
}
