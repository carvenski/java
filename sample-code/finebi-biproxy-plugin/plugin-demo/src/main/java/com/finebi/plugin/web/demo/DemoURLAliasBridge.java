package com.finebi.plugin.biproxy.demo;

import com.fr.decision.fun.impl.AbstractURLAliasProvider;
import com.fr.decision.webservice.url.alias.URLAlias;
import com.fr.decision.webservice.url.alias.URLAliasFactory;

public class DemoURLAliasBridge extends AbstractURLAliasProvider  {
    @Override
    public URLAlias[] registerAlias() {
        return new URLAlias[] {
        //访问路径 http://29.23.115.140:37799/webroot/decision/url/publish?id=90909
        URLAliasFactory.createPluginAlias("/publish", "/gogogo", true),
        };
    }
}