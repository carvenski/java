/*    */ package com.finebi.plugin.biproxy.core.persist.exception;
/*    */ 
/*    */ import com.fr.intelligence.IntelligenceRuntimeException;
/*    */ 
/*    */ public class DaoOperationNotSupportedException extends IntelligenceRuntimeException {
/*    */   private static final long serialVersionUID = 16150499725639866L;
/*    */   
/*    */   public String errorCode() {
/* 15 */     return "nat_2147483647";
/*    */   }
/*    */   
/*    */   public DaoOperationNotSupportedException() {
/* 19 */     super("dao operation is not supported");
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\core\persist\exception\DaoOperationNotSupportedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */