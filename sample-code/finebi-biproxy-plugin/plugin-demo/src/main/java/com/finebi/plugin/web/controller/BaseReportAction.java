/*    */ package com.finebi.plugin.biproxy.web.controller.report;
/*    */ 
/*    */ import com.finebi.plugin.biproxy.core.service.report.ReportService;
/*    */ import com.finebi.plugin.biproxy.core.service.report.impl.ReportServiceImpl;
/*    */ import com.finebi.plugin.biproxy.web.controller.BaseDynamicApi;
/*    */ import com.fr.third.springframework.stereotype.Controller;
/*    */ 
/*    */ @Controller
/*    */ public class BaseReportAction extends BaseDynamicApi {
/*    */   protected static ReportService getReportService() {
/* 16 */     return (ReportService)new ReportServiceImpl();
/*    */   }
/*    */ }