package com.gitee.linzl.chain.pattern;

public interface FilterChainHandler {
    void doFilter(ChainRequest request, ChainResponse response, FilterChain chain);
}
