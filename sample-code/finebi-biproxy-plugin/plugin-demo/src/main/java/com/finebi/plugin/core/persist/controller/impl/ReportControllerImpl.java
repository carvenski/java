/*    */ package com.finebi.plugin.biproxy.core.persist.controller.impl;
/*    */ 
/*    */ import com.finebi.plugin.biproxy.core.persist.controller.AbstractController;
/*    */ import com.finebi.plugin.biproxy.core.persist.controller.ReportController;
/*    */ import com.finebi.plugin.biproxy.core.persist.data.Report;
/*    */ import com.finebi.plugin.biproxy.core.persist.entity.ReportEntity;
/*    */ import com.finebi.plugin.biproxy.core.persist.exception.DaoOperationNotSupportedException;
/*    */ import com.finebi.plugin.biproxy.core.persist.util.EntityConvertUtil;

/*    */ import com.fr.stable.db.accessor.DBAccessor;
/*    */ import com.fr.stable.db.dao.DAOContext;
/*    */ import com.fr.stable.db.data.DataRecord;
/*    */ import com.fr.stable.db.entity.BaseEntity;
/*    */ import com.fr.stable.query.condition.QueryCondition;
/*    */ import com.fr.stable.query.data.DataList;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ public class ReportControllerImpl extends AbstractController implements ReportController {
/*    */   public ReportControllerImpl(DBAccessor dbAccessor) {
/* 21 */     super(dbAccessor);
/*    */   }
/*    */   
            // =================================
            // 这里面就是封装好的操作db crud的函数
            // https://finereport-overseas.github.io/developer-guide/tutorial/chapter_11/chapter_11.html

/*    */   // 重点使用这个函数 addOrUpdate
/*    */   public void addOrUpdate(Report report) throws Exception {
/* 79 */     this.dbAccessor.runDMLAction(daoContext -> {
/*    */           getReportDao(daoContext).addOrUpdate(
                        EntityConvertUtil.toReportEntity(report));
/*    */           return null;
/*    */         });
/*    */   }

/*    */   public void add(Report report) throws Exception {
/* 26 */     this.dbAccessor.runDMLAction(
                daoContext -> {
/*    */           getReportDao(daoContext).add(
                        EntityConvertUtil.toReportEntity(report));
/*    */           return null;
/*    */         });
/*    */   }
/*    */   
/*    */   public Report getById(String s) throws Exception {
/* 34 */     return (Report)this.dbAccessor.runQueryAction(
                daoContext -> EntityConvertUtil.toReport(
                (ReportEntity)getReportDao(daoContext).getById(s)));
/*    */   }
/*    */   
/*    */   public void update(Report report) throws Exception {
/* 39 */     this.dbAccessor.runDMLAction(daoContext -> {
/*    */           getReportDao(daoContext).update(EntityConvertUtil.toReportEntity(report));
/*    */           return null;
/*    */         });
/*    */   }
/*    */   
/*    */   public void remove(String s) throws Exception {
/* 47 */     this.dbAccessor.runDMLAction(daoContext -> {
/*    */           getReportDao(daoContext).remove(s);
/*    */           return null;
/*    */         });
/*    */   }
/*    */   
/*    */   public void remove(QueryCondition queryCondition) throws Exception {
/* 55 */     this.dbAccessor.runDMLAction(daoContext -> {
/*    */           getReportDao(daoContext).remove(queryCondition);
/*    */           return null;
/*    */         });
/*    */   }
/*    */   
/*    */   public List<Report> find(QueryCondition queryCondition) throws Exception {
/* 63 */     return (List<Report>)this.dbAccessor.runQueryAction(daoContext -> (List)getReportDao(daoContext).find(queryCondition).stream().map(EntityConvertUtil::toReport).collect(Collectors.toList()));
/*    */   }
/*    */   
/*    */   public Report findOne(QueryCondition queryCondition) throws Exception {
/* 68 */     return (Report)this.dbAccessor.runQueryAction(daoContext -> EntityConvertUtil.toReport((ReportEntity)getReportDao(daoContext).findOne(queryCondition)));
/*    */   }
/*    */   
/*    */   public DataList<Report> findWithTotalCount(QueryCondition queryCondition) throws Exception {
/* 74 */     throw new DaoOperationNotSupportedException();
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\core\persist\controller\impl\ReportControllerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */