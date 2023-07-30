/*    */ package com.finebi.plugin.biproxy.core.persist;
/*    */ 
/*    */ import com.finebi.plugin.biproxy.core.persist.controller.ReportController;
/*    */ import com.finebi.plugin.biproxy.core.persist.controller.impl.ReportControllerImpl;
/*    */ import com.fr.stable.db.accessor.DBAccessor;
/*    */ 
/*    */ public class PersistenceContext {
/* 13 */   private static final PersistenceContext instance = new PersistenceContext();
/*    */   
/*    */   private boolean initialized = false;
/*    */   
/*    */   private ReportController reportController;
/*    */   
/*    */   public static PersistenceContext getInstance() {
/* 20 */     return instance;
/*    */   }
/*    */   
/*    */   public PersistenceContext init(DBAccessor dbAccessor) throws Exception {
/* 24 */     synchronized (this) {
/* 25 */       if (this.initialized)
/* 26 */         throw new IllegalAccessException("PersistenceContext has already initialized"); 
/* 28 */       this.reportController = (ReportController)new ReportControllerImpl(dbAccessor);
/* 29 */       this.initialized = true;
/* 30 */       return this;
/*    */     } 
/*    */   }
/*    */   
/*    */   public ReportController getReportController() {
/* 36 */     return this.reportController;
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\core\persist\PersistenceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */