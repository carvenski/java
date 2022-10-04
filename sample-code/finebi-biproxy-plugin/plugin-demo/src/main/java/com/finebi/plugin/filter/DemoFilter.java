package com.finebi.plugin.biproxy;

// import com.fanruan.api.log.LogKit;
// import com.fr.web.utils.WebUtils;
import com.finebi.plugin.biproxy.logger.LogUtils;

import com.fr.decision.fun.GlobalRequestFilterProvider;
import com.fr.decision.fun.EmbedRequestFilterProvider;
import com.fr.decision.fun.impl.AbstractGlobalRequestFilterProvider;

import com.fr.intelli.record.Focus;
import com.fr.record.analyzer.EnableMetrics;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@EnableMetrics
public class DemoFilter extends AbstractGlobalRequestFilterProvider {

    @Override
    public String filterName() {
        return "DemoFilter";
    }

    @Override
    public String[] urlPatterns() {
        return new String[]{"/proxy/*"};
    }

    @Override
    @Focus(id="com.finebi.plugin.biproxy.DemoFilter",text = "报表转发插件")
    public void doFilter( HttpServletRequest req, HttpServletResponse res, FilterChain chain ){
        try{
            // 重定向
            LogUtils.info("=== DemoFilter ===");
            res.sendRedirect("http://baidu.com");
            // chain.doFilter(req,res);
        }catch (Exception e){
        }
    }
}