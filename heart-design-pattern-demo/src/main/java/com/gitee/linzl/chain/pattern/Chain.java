package com.gitee.linzl.chain.pattern;

import java.util.List;

public class Chain {
    public List<ChainHandler> handlers;

    private int index = 0;

    public Chain(List<ChainHandler> handlers) {
        this.handlers = handlers;
    }

    public void doFilter(ApplyRequest request, ApplyResponse response) {
        if (index >= handlers.size()) {
            return;
        }
        handlers.get(index++).doFilter(request, response, this);
    }
}
