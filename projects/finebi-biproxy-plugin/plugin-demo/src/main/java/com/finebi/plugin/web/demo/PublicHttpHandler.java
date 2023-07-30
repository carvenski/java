package com.finebi.plugin.biproxy.demo;

import java.util.*;
import com.finebi.plugin.biproxy.db.SystemConfigAccessBridge;
import com.finebi.plugin.biproxy.db.SystemConfigDAO;
import com.finebi.plugin.biproxy.db.SystemConfigEntity;
import com.fr.stable.db.action.DBAction;
import com.fr.stable.db.dao.DAOContext;
import com.fr.stable.query.restriction.RestrictionFactory;
import com.fr.stable.query.QueryFactory;

import com.fr.decision.fun.impl.BaseHttpHandler;
import com.fr.intelli.record.Focus;
import com.fr.intelli.record.Original;
import com.fr.record.analyzer.EnableMetrics;
import com.fr.third.springframework.web.bind.annotation.RequestMethod;
import com.finebi.plugin.biproxy.logger.LogUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EnableMetrics
public class PublicHttpHandler extends BaseHttpHandler {

    @Override
    public RequestMethod getMethod() {
        return RequestMethod.GET;
    }

    @Override
    public String getPath() {
        return "/gogogo";
    }

    @Override
    public boolean isPublic() {
        return true;   //转发接口不需要token认证
    }

    @Override
    @Focus(id = "com.finebi.plugin.biproxy", text = "", source = Original.PLUGIN)
    public void handle(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        long t0 = System.currentTimeMillis();
        LogUtils.info("=== 转发报表请求 ===");
        LogUtils.info("获取id参数: " + req.getQueryString().toString());
        // 解析id
        String id = req.getParameter("id");
        // 实时查询db
        List<SystemConfigEntity> list = SystemConfigAccessBridge
            .getDbAccessor().runQueryAction(
            new DBAction<List<SystemConfigEntity>>() {
            @Override
            public List<SystemConfigEntity> run(DAOContext daoContext) throws Exception {
                return daoContext.getDAO(SystemConfigDAO.class).find(
                  QueryFactory.create().addRestriction(
                     RestrictionFactory.eq("id", id)
                  ));
            }
        });
        if (list.size() > 0) {
            String url = list.get(0).getUrl();
            LogUtils.info("=== id: "+id+" | url: "+url+" ===");
            // 报表转发
            // req.getRequestDispatcher(url).forward(req, res);
            res.sendRedirect(url);
        } else {
            res.sendError(404, "亲...你的路径找不到...请先配置好转发规则奥");
        }
        LogUtils.info("=== 转发报表请求 finish ===");
        LogUtils.info(String.format(">>> 转发插件耗时= %dms", (System.currentTimeMillis() - t0)));
        return;
    }
}