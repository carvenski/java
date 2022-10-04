/*    */ package com.finebi.plugin.biproxy.core.service.report.bean;
/*    */ 
/*    */ public enum ReportType {
/*  9 */   REPORT_TYPE("report"),  // 报表预览地址 类型
/* 10 */   ENTRY_TYPE("entry");    // 报表目录地址 类似
/*    */   
/*    */   private final String type;
/*    */   
/*    */   ReportType(String type) {
/* 15 */     this.type = type;
/*    */   }
/*    */   
/*    */   public String getType() {
/* 19 */     return this.type;
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\core\service\report\bean\ReportType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */