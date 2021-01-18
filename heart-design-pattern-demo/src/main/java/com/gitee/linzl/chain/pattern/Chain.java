package com.gitee.linzl.chain.pattern;

import java.util.ArrayList;
import java.util.List;

public class Chain {
    public List<ChainHandler> handlers;

    private int index = 0;

    public Chain() {
        this.handlers = new ArrayList<>();
    }

    public Chain(List<ChainHandler> handlers) {
        this.handlers = handlers;
    }

    public void addLast(ChainHandler handler) {
        handlers.add(handlers.size(), handler);
    }

    public void doFilter(ApplyRequest request, ApplyResponse response) {
        if (index >= handlers.size()) {
            return;
        }
        handlers.get(index++).doFilter(request, response, this);
    }
}
