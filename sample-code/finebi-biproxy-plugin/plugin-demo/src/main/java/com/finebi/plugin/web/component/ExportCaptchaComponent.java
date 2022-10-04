/*    */ package com.finebi.plugin.biproxy.web.component;
/*    */ 
/*    */ import com.fr.plugin.context.PluginContexts;
/*    */ import com.fr.web.struct.Component;
/*    */ import com.fr.web.struct.Filter;
/*    */ import com.fr.web.struct.browser.RequestClient;
/*    */ import com.fr.web.struct.category.ScriptPath;
/*    */ import com.fr.web.struct.category.StylePath;
/*    */ 
/*    */ public class ExportCaptchaComponent extends Component {
/* 16 */   public static ExportCaptchaComponent KEY = new ExportCaptchaComponent();
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


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\web\component\ExportCaptchaComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */