package com.gitee.linzl.chain.pattern;

public interface ChainHandler {
    void doFilter(ApplyRequest request, ApplyResponse response, Chain chain);
}
