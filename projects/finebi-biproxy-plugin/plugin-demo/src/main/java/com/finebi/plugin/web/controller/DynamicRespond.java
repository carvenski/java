/*     */ package com.finebi.plugin.biproxy.web.controller.respond;
/*     */ 
/*     */ import com.finebi.foundation.api.reponse.FineRespond;
/*     */ import com.fr.decision.webservice.Response;
/*     */ import com.fr.stable.AssistUtils;
/*     */ import com.fr.stable.StringUtils;
/*     */ import java.util.Collections;
/*     */ 
/*     */ public class DynamicRespond {
/*     */   static final int SUCCESS_CODE = 0;
/*     */   
/*     */   static final int FAIL_CODE = 1;
/*     */   
/*     */   static final String FAIL = "fail";
/*     */   
/*     */   static final String SUCCESS = "success";
/*     */   
/*     */   private boolean success = true;
/*     */   
/*  21 */   private int code = 0;
/*     */   
/*  22 */   private String message = "success";
/*     */   
/*  23 */   private Object data = Collections.emptyList();
/*     */   
/*     */   private String errorCode;
/*     */   
/*     */   private Object detailErrorMsg;
/*     */   
/*     */   private String errorMsg;
/*     */   
/*     */   public DynamicRespond(FineRespond fineRespond) {
/*  29 */     String code = fineRespond.getCode();
/*  30 */     switch (code) {
/*     */       case "200":
/*  32 */         this.code = 0;
/*     */         break;
/*     */       case "500":
/*  36 */         this.code = 1;
/*     */         break;
/*     */     } 
/*  40 */     this.data = fineRespond.getData();
/*  41 */     this.detailErrorMsg = fineRespond.getDetailErrorMsg();
/*  42 */     this.errorCode = fineRespond.getErrorCode();
/*  43 */     this.errorMsg = fineRespond.getErrorMsg();
/*  44 */     this.message = fineRespond.getMessage();
/*  45 */     this.success = fineRespond.isSuccess();
/*     */   }
/*     */   
/*     */   public DynamicRespond(Response response) {
/*  49 */     String errorCode = response.getErrorCode();
/*  50 */     this.errorCode = errorCode;
/*  51 */     this.errorMsg = response.getErrorMsg();
/*  52 */     this.data = response.getData();
/*  53 */     if (StringUtils.isNotEmpty(errorCode)) {
/*  54 */       this.success = false;
/*  55 */       this.code = 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DynamicRespond success() {
/*  63 */     return success("success");
/*     */   }
/*     */   
/*     */   public static DynamicRespond success(Object data) {
/*  67 */     DynamicRespond dynamicRespond = new DynamicRespond();
/*  68 */     dynamicRespond.setData(data);
/*  69 */     return dynamicRespond;
/*     */   }
/*     */   
/*     */   public static DynamicRespond fail() {
/*  73 */     DynamicRespond dynamicRespond = new DynamicRespond();
/*  74 */     dynamicRespond.setCode(1);
/*  75 */     dynamicRespond.setMessage("fail");
/*  76 */     dynamicRespond.setSuccess(false);
/*  77 */     return dynamicRespond;
/*     */   }
/*     */   
/*     */   public static DynamicRespond fail(String errorCode, String errorMsg) {
/*  81 */     DynamicRespond dynamicRespond = fail();
/*  82 */     dynamicRespond.setErrorCode(errorCode);
/*  83 */     dynamicRespond.setErrorMsg(errorMsg);
/*  84 */     return dynamicRespond;
/*     */   }
/*     */   
/*     */   public static DynamicRespond fail(String errorCode) {
/*  88 */     DynamicRespond dynamicRespond = fail();
/*  89 */     dynamicRespond.setErrorCode(errorCode);
/*  90 */     return dynamicRespond;
/*     */   }
/*     */   
/*     */   public static DynamicRespond fail(String errorCode, String errorMsg, Object detailErrorMsg) {
/*  94 */     DynamicRespond dynamicRespond = fail();
/*  95 */     dynamicRespond.setErrorCode(errorCode);
/*  96 */     dynamicRespond.setErrorMsg(errorMsg);
/*  97 */     dynamicRespond.setDetailErrorMsg(detailErrorMsg);
/*  98 */     return dynamicRespond;
/*     */   }
/*     */   
/*     */   public int getCode() {
/* 102 */     return this.code;
/*     */   }
/*     */   
/*     */   public void setCode(int code) {
/* 106 */     this.code = code;
/*     */   }
/*     */   
/*     */   public String getErrorCode() {
/* 110 */     return this.errorCode;
/*     */   }
/*     */   
/*     */   public void setErrorCode(String errorCode) {
/* 114 */     this.errorCode = errorCode;
/*     */   }
/*     */   
/*     */   public String getMessage() {
/* 118 */     return this.message;
/*     */   }
/*     */   
/*     */   public void setMessage(String message) {
/* 122 */     this.message = message;
/*     */   }
/*     */   
/*     */   public Object getData() {
/* 126 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setData(Object data) {
/* 130 */     this.data = data;
/*     */   }
/*     */   
/*     */   public boolean isSuccess() {
/* 134 */     return this.success;
/*     */   }
/*     */   
/*     */   public void setSuccess(boolean success) {
/* 138 */     this.success = success;
/*     */   }
/*     */   
/*     */   public Object getDetailErrorMsg() {
/* 142 */     return this.detailErrorMsg;
/*     */   }
/*     */   
/*     */   public void setDetailErrorMsg(Object detailErrorMsg) {
/* 146 */     this.detailErrorMsg = detailErrorMsg;
/*     */   }
/*     */   
/*     */   public String getErrorMsg() {
/* 150 */     return this.errorMsg;
/*     */   }
/*     */   
/*     */   public void setErrorMsg(String errorMsg) {
/* 154 */     this.errorMsg = errorMsg;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 158 */     return (o instanceof DynamicRespond && AssistUtils.equals(Boolean.valueOf(this.success), Boolean.valueOf(((DynamicRespond)o).success)) && AssistUtils.equals(this.code, ((DynamicRespond)o).code) && AssistUtils.equals(this.message, ((DynamicRespond)o).message) && AssistUtils.equals(this.data, ((DynamicRespond)o).data) && AssistUtils.equals(this.errorCode, ((DynamicRespond)o).errorCode) && AssistUtils.equals(this.detailErrorMsg, ((DynamicRespond)o).detailErrorMsg) && AssistUtils.equals(this.errorMsg, ((DynamicRespond)o).errorMsg));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 162 */     return AssistUtils.hashCode(new Object[] { Integer.valueOf(this.code), this.message, this.data, Boolean.valueOf(this.success), this.errorCode, this.detailErrorMsg, this.errorMsg });
/*     */   }
/*     */   
/*     */   public String toString() {
/* 166 */     return AssistUtils.toString(this);
/*     */   }
/*     */   
/*     */   public DynamicRespond clone() {
/* 170 */     DynamicRespond clone = new DynamicRespond();
/* 171 */     clone.setSuccess(this.success);
/* 172 */     clone.setCode(this.code);
/* 173 */     clone.setMessage(this.message);
/* 174 */     clone.setErrorCode(this.errorCode);
/* 175 */     clone.setErrorMsg(this.errorMsg);
/* 176 */     clone.setData(this.data);
/* 177 */     clone.setDetailErrorMsg(this.detailErrorMsg);
/* 178 */     return clone;
/*     */   }
/*     */   
/*     */   public DynamicRespond() {}
/*     */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\web\controller\respond\DynamicRespond.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */