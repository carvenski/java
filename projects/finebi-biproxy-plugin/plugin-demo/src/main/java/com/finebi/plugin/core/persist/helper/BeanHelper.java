/*    */ package com.finebi.plugin.biproxy.core.persist.helper;
/*    */ 
/*    */ import com.fr.third.fasterxml.jackson.databind.ObjectMapper;
/*    */ 
/*    */ public class BeanHelper {
/* 11 */   private static final ObjectMapper objectMapper = new ObjectMapper();
/*    */   
/*    */   public static ObjectMapper mapper() {
/* 14 */     return objectMapper;
/*    */   }
/*    */   
/*    */   public static <T> T cast(Object obj) {
/* 19 */     return (T)obj;
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\core\persist\helper\BeanHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */