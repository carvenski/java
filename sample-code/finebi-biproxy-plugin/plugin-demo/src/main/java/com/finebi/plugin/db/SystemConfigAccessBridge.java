package com.finebi.plugin.biproxy.db;

import com.finebi.plugin.biproxy.db.SystemConfigDAO;
import com.finebi.plugin.biproxy.db.SystemConfigEntity;
import com.fr.db.fun.impl.AbstractDBAccessProvider;
import com.fr.intelli.record.Focus;
import com.fr.intelli.record.Original;
import com.fr.record.analyzer.EnableMetrics;
import com.fr.stable.db.accessor.DBAccessor;
import com.fr.stable.db.dao.BaseDAO;
import com.fr.stable.db.dao.DAOProvider;

@EnableMetrics
public class SystemConfigAccessBridge extends AbstractDBAccessProvider {

    private static DBAccessor dbAccessor = null;

    @Focus(id="com.finebi.plugin.biproxy", text = "报表转发插件", source = Original.PLUGIN)
    public static DBAccessor getDbAccessor() {
        return dbAccessor;
    }

    @Override
    public DAOProvider[] registerDAO() {
        return new DAOProvider[]{
                new DAOProvider() {
                    @Override
                    public Class getEntityClass() {
                        return SystemConfigEntity.class;
                    }

                    @Override
                    public Class<? extends BaseDAO> getDAOClass() {
                        return SystemConfigDAO.class;
                    }
                }
        };
    }

    @Override
    public void onDBAvailable(DBAccessor dbAccessor) {
        SystemConfigAccessBridge.dbAccessor = dbAccessor;
    }
}
