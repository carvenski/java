/*    */ package com.finebi.plugin.biproxy.util.plugin;
/*    */ 
/*    */ import com.fr.plugin.context.PluginContexts;
/*    */ 
/*    */ public class PluginUtils {
/*    */   public static boolean checkPluginLic() {
/* 13 */     return PluginContexts.currentContext().isAvailable();
/*    */   }
/*    */ }

