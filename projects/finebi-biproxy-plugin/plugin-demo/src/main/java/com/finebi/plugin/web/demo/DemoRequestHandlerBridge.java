package com.finebi.plugin.biproxy.demo;

import com.fr.decision.fun.impl.AbstractHttpHandlerProvider;
import com.fr.decision.fun.impl.BaseHttpHandler;
import com.finebi.plugin.biproxy.demo.PublicHttpHandler;

public class DemoRequestHandlerBridge extends AbstractHttpHandlerProvider {
    @Override
    public BaseHttpHandler[] registerHandlers() {
        return new BaseHttpHandler[]{
                new PublicHttpHandler(),
        };
    }
}