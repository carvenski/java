package com.finebi.plugin.biproxy.db;

import com.finebi.plugin.biproxy.db.SystemConfigEntity;
import com.fr.stable.db.dao.BaseDAO;
import com.fr.stable.db.session.DAOSession;

public class SystemConfigDAO extends BaseDAO<SystemConfigEntity> {

    public SystemConfigDAO(DAOSession daoSession) {
        super(daoSession);
    }

    @Override
    protected Class<SystemConfigEntity> getEntityClass() {
        return SystemConfigEntity.class;
    }
}
