/*    */ package com.finebi.plugin.biproxy.core.service.report.impl;
/*    */ 
/*    */ import com.finebi.plugin.biproxy.core.persist.PersistenceContext;
/*    */ import com.finebi.plugin.biproxy.core.persist.data.Report;
/*    */ import com.finebi.plugin.biproxy.core.service.report.ReportService;
/*    */ import com.finebi.plugin.biproxy.core.service.report.bean.AbstractReportBean;
/*    */ import com.finebi.plugin.biproxy.core.service.report.bean.ReportBean;
/*    */ import com.finebi.plugin.biproxy.core.service.report.bean.ReportEntryBean;
/*    */ import com.finebi.plugin.biproxy.core.service.report.bean.ReportType;
/*    */ import com.finebi.plugin.biproxy.core.service.report.bean.UpdateReportsBean;
/*    */ import com.finebi.plugin.biproxy.util.plugin.PluginUtils;
/*    */ import com.fr.stable.query.QueryFactory;
/*    */ import com.fr.stable.query.restriction.RestrictionFactory;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ReportServiceImpl implements ReportService {

            // 查询全部list
/*    */   public List<ReportBean> getAllReportBeans() throws Exception {            
/* 37 */     List<Report> reports = PersistenceContext
                .getInstance()
                .getReportController()
                .find(
                    // 查询条件
                    QueryFactory.create().addRestriction(                        
                        RestrictionFactory.eq("type", "")
                ));
/* 38 */     List<ReportBean> reportBeans = new ArrayList<>();
/* 39 */     if (reports != null && !reports.isEmpty())
/* 40 */       for (Report report : reports) {
/* 41 */         ReportBean bean = new ReportBean();
/* 43 */         reportBeans.add(bean);
/*    */       }  
/* 46 */     return reportBeans;
/*    */   }
/*    */   
/*    */   public void removeReports(UpdateReportsBean updateReportsBean) throws Exception {
/* 70 */     List<String> ids = updateReportsBean.getRemoveReports();
/* 71 */     for (String id : ids)
/* 72 */       PersistenceContext
                .getInstance()
                .getReportController()
                .remove(id); 
/*    */   }
/*    */   
/*    */   public void addReports(UpdateReportsBean updateReportsBean) throws Exception {
                String type = updateReportsBean.getType();
                List<String> ids = updateReportsBean.getAddReports();
                for (String id : ids) {
                   Report report = new Report();
                   report.setType(type);
                   report.setId(id);
                    // 新增和更新记录
                    PersistenceContext
                    .getInstance()
                    .getReportController()
                    .addOrUpdate(report);
               } 
            }
}

