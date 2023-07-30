/*    */ package com.finebi.plugin.biproxy.web.controller;
/*    */ 
/*    */ import com.finebi.plugin.biproxy.logger.LogUtils;
/*    */ import com.finebi.plugin.biproxy.web.controller.respond.DynamicRespond;
/*    */ import com.finebi.web.action.v5.BaseAction;
/*    */ import com.fr.decision.webservice.exception.login.LoginInfoNotAvailableException;
/*    */ import com.fr.decision.webservice.v10.login.LoginService;
/*    */ import com.fr.json.JSONObject;
/*    */ import com.fr.stable.StringUtils;
/*    */ import com.fr.third.springframework.stereotype.Controller;
/*    */ import com.fr.third.springframework.web.bind.annotation.ExceptionHandler;
/*    */ import com.fr.third.springframework.web.bind.annotation.ResponseBody;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ @Controller
/*    */ public class BaseDynamicApi extends BaseAction {
    
/*    */   @ExceptionHandler
/*    */   @ResponseBody
/*    */   public DynamicRespond exceptionRespond(Exception ex) {
/* 31 */     LogUtils.error(ex);
/* 32 */     return new DynamicRespond(getExceptionRespond(ex));
/*    */   }
/*    */   
/*    */   protected static JSONObject receiveJSONPara(HttpServletRequest req) throws IOException, UnsupportedEncodingException {
/* 44 */     JSONObject result = JSONObject.create();
/*    */     try {
/* 46 */       BufferedReader br = new BufferedReader(new InputStreamReader((InputStream)req.getInputStream(), "utf-8"));
/* 48 */       StringBuilder sb = new StringBuilder();
/*    */       String line;
/* 49 */       while ((line = br.readLine()) != null)
/* 50 */         sb.append(line); 
/* 52 */       return new JSONObject(sb.toString());
/* 53 */     } catch (Exception e) {
/* 54 */       LogUtils.error(e);
/* 56 */       return result;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected static void checkLoginUser(HttpServletRequest req) throws Exception {
/* 67 */     String currentUserName = LoginService.getInstance().getUserNameFromRequest(req);
/* 68 */     if (StringUtils.isEmpty(currentUserName))
/* 69 */       throw new LoginInfoNotAvailableException(); 
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\web\controller\BaseDynamicApi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */