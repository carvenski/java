/*    */ package com.finebi.plugin.biproxy.core.persist.entity;
/*    */ 
/*    */ import com.fr.stable.db.entity.BaseEntity;
/*    */ import com.fr.third.javax.persistence.Column;
/*    */ import com.fr.third.javax.persistence.Entity;
/*    */ import com.fr.third.javax.persistence.Table;
/*    */ 
        // 描述表结构的地方，
/*    */ @Entity
/*    */ @Table(name = "fine_plugin_biproxy_report_url_map")
/*    */ public class ReportEntity extends BaseEntity {
/*    */   private static final long serialVersionUID = 1104913682347161251L;
/*    */   
/*    */   public static final String COLUMN_TYPE = "type";
/*    */   public static final String COLUMN_DESC = "desc";
/*    */   
/*    */   @Column(name = "type", nullable = false, length=255) //存储报表内部访问地址
/*    */   private String type;

/*    */   @Column(name = "desc", nullable = false, length=255) //存储报表名称等描述信息
/*    */   private String desc;
/*    */   

           public ReportEntity(){

           }
           
/*    */   public String getType() {
/* 21 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(String type) {
/* 25 */     this.type = type;
/*    */   }

/*    */   public String getDesc() {
/* 21 */     return this.desc;
/*    */   }
/*    */   
/*    */   public void setDesc(String desc) {
/* 25 */     this.desc = desc;
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\core\persist\entity\ReportEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */