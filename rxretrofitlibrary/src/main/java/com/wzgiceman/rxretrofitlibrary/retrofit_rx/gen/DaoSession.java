package com.wzgiceman.rxretrofitlibrary.retrofit_rx.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie.CookieResulte;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.gen.DownInfoDao;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.gen.CookieResulteDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig downInfoDaoConfig;
    private final DaoConfig cookieResulteDaoConfig;

    private final DownInfoDao downInfoDao;
    private final CookieResulteDao cookieResulteDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        downInfoDaoConfig = daoConfigMap.get(DownInfoDao.class).clone();
        downInfoDaoConfig.initIdentityScope(type);

        cookieResulteDaoConfig = daoConfigMap.get(CookieResulteDao.class).clone();
        cookieResulteDaoConfig.initIdentityScope(type);

        downInfoDao = new DownInfoDao(downInfoDaoConfig, this);
        cookieResulteDao = new CookieResulteDao(cookieResulteDaoConfig, this);

        registerDao(DownInfo.class, downInfoDao);
        registerDao(CookieResulte.class, cookieResulteDao);
    }
    
    public void clear() {
        downInfoDaoConfig.clearIdentityScope();
        cookieResulteDaoConfig.clearIdentityScope();
    }

    public DownInfoDao getDownInfoDao() {
        return downInfoDao;
    }

    public CookieResulteDao getCookieResulteDao() {
        return cookieResulteDao;
    }

}
