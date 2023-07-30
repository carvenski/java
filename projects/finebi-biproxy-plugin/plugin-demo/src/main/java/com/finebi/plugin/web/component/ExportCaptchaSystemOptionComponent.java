/*    */ package com.finebi.plugin.biproxy.web.component;
/*    */ 
/*    */ import com.fr.plugin.context.PluginContexts;
/*    */ import com.fr.web.struct.Component;
/*    */ import com.fr.web.struct.Filter;
/*    */ import com.fr.web.struct.browser.RequestClient;
/*    */ import com.fr.web.struct.category.ScriptPath;
/*    */ import com.fr.web.struct.category.StylePath;
/*    */ 
/*    */ public class ExportCaptchaSystemOptionComponent extends Component {
/* 16 */   public static ExportCaptchaSystemOptionComponent KEY = new ExportCaptchaSystemOptionComponent();
/*    */   
/*    */   public ScriptPath script(RequestClient requestClient) {
/* 24 */     return ScriptPath.build("com/finebi/plugin/web/system/bundle.js");
/*    */   }
/*    */   
/*    */   public StylePath style(RequestClient requestClient) {
/* 29 */     return StylePath.build("com/finebi/plugin/web/system/bundle.css");
/*    */   }
/*    */   
/*    */   public Filter filter() {
/* 34 */     return PluginContexts.currentContext()::isAvailable;
/*    */   }
/*    */ }