package com.gitee.linzl.chain.pattern;

public interface ChainHandler {
    void doFilter(ChainRequest request, ChainResponse response, Chain chain);
}
