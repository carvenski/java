/*    */ package com.finebi.plugin.biproxy.interfaces;
/*    */ 
/*    */ import com.finebi.foundation.api.web.component.AssembleComponentFactory;
/*    */ import com.fr.decision.fun.impl.AbstractWebResourceProvider;
/*    */ import com.fr.plugin.context.PluginContext;
/*    */ import com.fr.plugin.context.PluginContexts;
/*    */ import com.fr.web.struct.Atom;
/*    */ import com.fr.web.struct.Component;
/*    */ import com.fr.web.struct.Filter;
/*    */ import com.fr.web.struct.browser.RequestClient;
/*    */ import com.fr.web.struct.category.ScriptPath;
/*    */ import com.fr.web.struct.category.StylePath;
/*    */ 
/*    */ public class BIShowExportCaptchaWebResourceProviderImpl extends AbstractWebResourceProvider {
/*    */   public Atom attach() {
/* 22 */     return (Atom)AssembleComponentFactory.getShowComponent();
/*    */   }
/*    */   
/*    */   public Atom client() {
/* 27 */     final PluginContext pluginContext = PluginContexts.currentContext();
/* 28 */     return (Atom)new Component() {
/*    */         public ScriptPath script(RequestClient requestClient) {
/* 32 */           return ScriptPath.build("com/finebi/plugin/web/show/export_captcha.js");
/*    */         }
/*    */         
/*    */         public StylePath style(RequestClient requestClient) {
/* 37 */           return StylePath.EMPTY;
/*    */         }
/*    */         
/*    */         public Filter filter() {
/* 42 */           return pluginContext::isAvailable;
/*    */         }
/*    */       };
/*    */   }
/*    */ }
