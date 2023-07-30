package com.finebi.plugin.biproxy;

import com.finebi.plugin.biproxy.logger.LogUtils;

import com.finebi.plugin.biproxy.logger.LogUtils;
import com.fr.decision.fun.GlobalRequestFilterProvider;
import com.fr.decision.fun.impl.AbstractGlobalRequestFilterProvider;
import com.fr.decision.fun.impl.AbstractEmbedRequestFilterProvider;

import com.fr.intelli.record.Focus;
import com.fr.record.analyzer.EnableMetrics;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@EnableMetrics
public class DemoFilter2 extends AbstractEmbedRequestFilterProvider {    

    @Override
    @Focus(id="com.finebi.plugin.biproxy.DemoFilter2",text = "报表转发插件")
    public void filter( HttpServletRequest req, HttpServletResponse res){
        try{
            // 重定向
            // 会过滤/webroot/decision/*
            if (req.getRequestURL().toString().contains("proxy")){
                LogUtils.info("=== DemoFilter2 ===");
                res.sendRedirect("http://baidu.com"); //没效果
            }
        }catch (Exception e){
        }
    }
}