/*    */ package com.finebi.plugin.biproxy.interfaces;
/*    */ 
/*    */ import com.finebi.plugin.biproxy.web.component.ExportCaptchaSystemOptionComponent;
/*    */ import com.fr.decision.fun.impl.AbstractSystemOptionProvider;
/*    */ import com.fr.decision.web.MainComponent;
/*    */ import com.fr.stable.fun.mark.API;
/*    */ import com.fr.web.struct.Atom;
/*    */ 
/*    */ @API(level = 1)
/*    */ public class ExportCaptchaOption extends AbstractSystemOptionProvider {
/*    */   public String id() {
/* 22 */     return "plugin-decision-management-nat-export-captcha-ZZZ";
/*    */   }
/*    */   
/*    */   public String displayName() {
/* 27 */     return "报表转发";
/*    */   }
/*    */   
/*    */   public int sortIndex() {
/* 32 */     return 2;
/*    */   }
/*    */   
/*    */   public Atom attach() {
/* 37 */     return (Atom)MainComponent.KEY;
/*    */   }
/*    */   
/*    */   public Atom client() {
/* 42 */     return (Atom)ExportCaptchaSystemOptionComponent.KEY;
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\interfaces\ExportCaptchaOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */