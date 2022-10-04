/*    */ package com.finebi.plugin.biproxy.core.service.report.bean;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class UpdateReportsBean {
/*    */   private String type;
/*    */   
/*    */   private List<String> addReports;
/*    */   
/*    */   private List<String> removeReports;
/*    */   
/*    */   public List<String> getAddReports() {
/* 16 */     return this.addReports;
/*    */   }
/*    */   
/*    */   public void setAddReports(List<String> addReports) {
/* 20 */     this.addReports = addReports;
/*    */   }
/*    */   
/*    */   public List<String> getRemoveReports() {
/* 24 */     return this.removeReports;
/*    */   }
/*    */   
/*    */   public void setRemoveReports(List<String> removeReports) {
/* 28 */     this.removeReports = removeReports;
/*    */   }
/*    */   
/*    */   public String getType() {
/* 32 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(String type) {
/* 36 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\core\service\report\bean\UpdateReportsBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */