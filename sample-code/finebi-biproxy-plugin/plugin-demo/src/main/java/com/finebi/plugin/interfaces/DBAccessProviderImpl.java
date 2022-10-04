/*    */ package com.finebi.plugin.biproxy.interfaces;
/*    */ 
/*    */ import com.finebi.plugin.biproxy.core.persist.PersistenceContext;
/*    */ import com.finebi.plugin.biproxy.core.persist.dao.ReportDao;
/*    */ import com.finebi.plugin.biproxy.core.persist.entity.ReportEntity;
/*    */ import com.finebi.plugin.biproxy.logger.LogUtils;
/*    */ import com.fr.plugin.db.AbstractDBAccessProvider;
/*    */ import com.fr.stable.db.accessor.DBAccessor;
/*    */ import com.fr.stable.db.dao.DAOProvider;        
/*    */ 
import com.fr.intelli.record.Focus;
import com.fr.intelli.record.Original;
import com.fr.record.analyzer.EnableMetrics;
import com.fr.stable.db.dao.BaseDAO;

         @EnableMetrics
/*    */ public class DBAccessProviderImpl extends AbstractDBAccessProvider {
/*    */   private static DBAccessor dbAccessor;

/*    */   @Focus(id="com.finebi.plugin.biproxy", text = "报表转发插件", source = Original.PLUGIN)
/*    */   public static DBAccessor getDbAccessor() {
/* 18 */     return dbAccessor;
/*    */   }

/*    */   @Override
/*    */   public DAOProvider[] registerDAO() { 
/* 23 */     return new DAOProvider[] { 
              new DAOProvider() {
                    @Override
                    public Class getEntityClass() {
                        return ReportEntity.class;
                    }

                    @Override
                    public Class<? extends BaseDAO> getDAOClass() {
                        return ReportDao.class;
                    }
                }
            };
/*    */   }

/*    */   @Override
/*    */   public void onDBAvailable(DBAccessor dbAccessor) {
             LogUtils.info("=== in DBAccessProviderImpl.onDBAvailable() ===");
             LogUtils.info(dbAccessor.toString());
/* 30 */     DBAccessProviderImpl.dbAccessor = dbAccessor;
/*    */     try {
/* 32 */        PersistenceContext.getInstance().init(dbAccessor);
/* 33 */     } catch (Exception e) {
/* 34 */       LogUtils.error("=== DBAccessProviderImpl Error !!! ===");
/* 34 */       LogUtils.error(e);
/*    */     } 
/*    */   }
/*    */ }

