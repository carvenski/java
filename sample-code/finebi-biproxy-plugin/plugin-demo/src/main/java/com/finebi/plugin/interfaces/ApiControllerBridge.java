/*    */ package com.finebi.plugin.biproxy.interfaces;
/*    */ 
/*    */ import com.finebi.plugin.biproxy.web.controller.report.ReportManageAction;
/*    */ import com.fr.decision.fun.impl.AbstractControllerRegisterProvider;
/*    */ import com.fr.intelli.record.Focus;
/*    */ import com.fr.intelli.record.Original;
/*    */ import com.fr.record.analyzer.EnableMetrics;
/*    */ import com.fr.stable.fun.Authorize;
/*    */ 
        
     @EnableMetrics
     // @Authorize(callSignKey = "com.finebi.plugin.biproxy")
     public class ApiControllerBridge extends AbstractControllerRegisterProvider {
       
       @Focus(id = "com.finebi.plugin.biproxy", text = "报表转发插件", source = Original.PLUGIN)
       public Class<?>[] getControllers() {
            return new Class[] { ReportManageAction.class };
       }

     }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\interfaces\ApiControllerBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */