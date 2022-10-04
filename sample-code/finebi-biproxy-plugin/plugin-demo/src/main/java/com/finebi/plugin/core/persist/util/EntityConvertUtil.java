/*    */ package com.finebi.plugin.biproxy.core.persist.util;
/*    */ 
/*    */ import com.finebi.plugin.biproxy.core.persist.data.Report;
/*    */ import com.finebi.plugin.biproxy.core.persist.entity.ReportEntity;
/*    */ 
/*    */ public class EntityConvertUtil {
/*    */   public static Report toReport(ReportEntity entity) {
/* 13 */     if (entity == null)
/* 14 */       return null; 
/* 16 */     Report report = new Report();
/* 17 */     report.setId(entity.getId());
/* 18 */     report.setType(entity.getType());
/* 18 */     report.setDesc(entity.getDesc());
/* 19 */     return report;
/*    */   }
/*    */   
/*    */   public static ReportEntity toReportEntity(Report report) {
/* 23 */     if (report == null)
/* 24 */       return null; 
/* 26 */     ReportEntity entity = new ReportEntity();
/* 27 */     entity.setId(report.getId());
/* 28 */     entity.setType(report.getType());
/* 18 */     entity.setDesc(report.getDesc());
/* 29 */     return entity;
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\core\persis\\util\EntityConvertUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */