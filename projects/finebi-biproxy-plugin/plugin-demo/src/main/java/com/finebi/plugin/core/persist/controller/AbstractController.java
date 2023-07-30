/*    */ package com.finebi.plugin.biproxy.core.persist.controller;
/*    */ 
/*    */ import com.finebi.plugin.biproxy.core.persist.dao.ReportDao;
/*    */ import com.fr.stable.db.accessor.DBAccessor;
/*    */ import com.fr.stable.db.dao.DAOContext;
/*    */ 
/*    */ public abstract class AbstractController {
/*    */   protected final DBAccessor dbAccessor;
/*    */   
/*    */   public AbstractController(DBAccessor dbAccessor) {
/* 16 */     this.dbAccessor = dbAccessor;
/*    */   }
/*    */   
/*    */   public static ReportDao getReportDao(DAOContext daoContext) {
/* 20 */     return (ReportDao)daoContext.getDAO(ReportDao.class);
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\core\persist\controller\AbstractController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */