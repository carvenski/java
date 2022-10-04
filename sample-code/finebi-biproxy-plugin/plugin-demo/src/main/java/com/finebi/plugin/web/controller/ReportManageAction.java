package com.finebi.plugin.biproxy.web.controller.report;
 
import com.finebi.plugin.biproxy.db.SystemConfigAccessBridge;
import com.finebi.plugin.biproxy.db.SystemConfigDAO;
import com.finebi.plugin.biproxy.db.SystemConfigEntity;
import com.finebi.plugin.biproxy.db.SystemConfigBean;
import com.fr.stable.db.action.DBAction;
import com.fr.stable.db.dao.DAOContext;
import com.fr.stable.query.restriction.RestrictionFactory;

import com.finebi.plugin.biproxy.logger.LogUtils;
import com.finebi.plugin.biproxy.web.controller.respond.DynamicRespond;
import com.fr.third.springframework.stereotype.Controller;
import com.fr.third.springframework.web.bind.annotation.RequestBody;
import com.fr.third.springframework.web.bind.annotation.RequestMapping;
import com.fr.third.springframework.web.bind.annotation.RequestMethod;
import com.fr.third.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.stable.query.QueryFactory;
import java.util.*;

@Controller
@RequestMapping({"publish"})
public class ReportManageAction extends BaseReportAction {

   // gmap热加载自mysql表,存储转发规则
   // 集群需要加个定时更新,否则其他机器上不是最新的gmap
   // public HashMap<String,String> gmap = new HashMap<String,String>();
   // public void hotoad_gmap() {
   //    try{
   //    LogUtils.info("== 热加载gmap ==");
   //    // 查询db
   //    List<SystemConfigEntity> list = SystemConfigAccessBridge
   //       .getDbAccessor().runQueryAction(
   //       new DBAction<List<SystemConfigEntity>>() {
   //       @Override
   //       public List<SystemConfigEntity> run(DAOContext daoContext) throws Exception {
   //           return daoContext.getDAO(SystemConfigDAO.class).find(
   //             QueryFactory.create().addRestriction(
   //                RestrictionFactory.gte("id", "")
   //             ));
   //       }
   //    });
   //    // 更新gmap
   //    for (SystemConfigEntity row: list){
   //       if (!this.gmap.containsKey(row.getId())){
   //          this.gmap.put(row.getId(), row.getUrl());
   //       }
   //    }
   //    LogUtils.info(list.toString());
   //    LogUtils.info(this.gmap.toString());
   //    LogUtils.info("== 热加载gmap 完成 ==");
   //    }catch(Exception e){
   //       LogUtils.info(e.getMessage());
   //    }
   // }

   // 获取全部list
   @RequestMapping(value = {"/getAll"}, method = {RequestMethod.GET})
   @ResponseBody
   public DynamicRespond getAll(
      HttpServletRequest req, 
      HttpServletResponse res) throws Exception {
         List<SystemConfigEntity> list = SystemConfigAccessBridge
            .getDbAccessor().runQueryAction(
            new DBAction<List<SystemConfigEntity>>() {
            @Override
            public List<SystemConfigEntity> run(DAOContext daoContext) throws Exception {
                return daoContext.getDAO(SystemConfigDAO.class).find(
                  QueryFactory.create().addRestriction(
                     RestrictionFactory.gte("id", "")
                  ));
            }
         });         
         List<SystemConfigBean> list2 = new ArrayList<SystemConfigBean>();
         for (SystemConfigEntity row: list){
            SystemConfigBean sysBean = new SystemConfigBean();
            sysBean.setId(row.getId());
            sysBean.setName(row.getName());
            sysBean.setUrl(row.getUrl());
            sysBean.setTime(row.getTime());
            list2.add(sysBean);
         };         
         LogUtils.info("== 获取全部list ==");
         return DynamicRespond.success(list2);
   }   
   
   // 更新或新增
   @RequestMapping(value = {"/addOrUpdate"}, method = {RequestMethod.POST})
   @ResponseBody
   public DynamicRespond addReport(
       HttpServletRequest req, 
       HttpServletResponse res, 
       @RequestBody SystemConfigBean bean) throws Exception {

         SystemConfigEntity result = SystemConfigAccessBridge
            .getDbAccessor().runDMLAction(
            new DBAction<SystemConfigEntity>() {
            @Override
            public SystemConfigEntity run(DAOContext daoContext) throws Exception {
                SystemConfigEntity entity = new SystemConfigEntity();
                entity.setId(bean.getId());
                entity.setName(bean.getName());                
                entity.setUrl(bean.getUrl());
                entity.setTime(new Date());
                daoContext.getDAO(SystemConfigDAO.class).addOrUpdate(entity);
                return entity;
            }
         });
         LogUtils.info("== 更新成功 ==");
         return DynamicRespond.success();
   }

   // 删除
   @RequestMapping(value = {"/remove"}, method = {RequestMethod.POST})
   @ResponseBody
   public DynamicRespond removeReport(
      HttpServletRequest req, 
      HttpServletResponse res, 
      @RequestBody SystemConfigBean bean) throws Exception {

         SystemConfigEntity result = SystemConfigAccessBridge
            .getDbAccessor().runDMLAction(
            new DBAction<SystemConfigEntity>() {
            @Override
            public SystemConfigEntity run(DAOContext daoContext) throws Exception {
                daoContext.getDAO(SystemConfigDAO.class).remove(
                  QueryFactory.create().addRestriction(
                     RestrictionFactory.eq("id", bean.getId())
                  ));
                return null;
            }
         });
         LogUtils.info("== 删除成功 ==");
         return DynamicRespond.success();
   }
   

}