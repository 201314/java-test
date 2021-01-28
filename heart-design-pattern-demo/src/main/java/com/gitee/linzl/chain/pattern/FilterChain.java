package com.gitee.linzl.chain.pattern;

import java.util.List;

public class FilterChain {
    public List<FilterChainHandler> handlers;

    private int index = 0;

    public FilterChain(List<FilterChainHandler> handlers) {
        this.handlers = handlers;
    }

    public void doFilter(ChainRequest request, ChainResponse response) {
        if (index >= handlers.size()) {
            return;
        }
        handlers.get(index++).doFilter(request, response, this);
    }
}
