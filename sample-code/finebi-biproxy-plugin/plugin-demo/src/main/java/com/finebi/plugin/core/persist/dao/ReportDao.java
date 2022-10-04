/*    */ package com.finebi.plugin.biproxy.core.persist.dao;
/*    */ 
/*    */ import com.finebi.plugin.biproxy.core.persist.entity.ReportEntity;
/*    */ import com.fr.stable.db.dao.BaseDAO;
/*    */ import com.fr.stable.db.dao.DAOProvider;
/*    */ import com.fr.stable.db.session.DAOSession;
/*    */ 
/*    */ public class ReportDao extends BaseDAO<ReportEntity> {
/* 10 */   public static final DAOProvider<?> DAO_PROVIDER = new DAOProvider<ReportEntity>() {
/*    */       public Class<ReportEntity> getEntityClass() {
/* 13 */         return ReportEntity.class;
/*    */       }
/*    */       
/*    */       public Class<? extends BaseDAO<ReportEntity>> getDAOClass() {
/* 18 */         return (Class)ReportDao.class;
/*    */       }
/*    */     };
/*    */   
/*    */   public ReportDao(DAOSession daoSession) {
/* 23 */     super(daoSession);
/*    */   }
/*    */   
/*    */   protected Class<ReportEntity> getEntityClass() {
/* 28 */     return ReportEntity.class;
/*    */   }
/*    */ }


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\core\persist\dao\ReportDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */