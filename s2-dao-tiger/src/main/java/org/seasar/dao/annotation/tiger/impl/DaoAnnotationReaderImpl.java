/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.dao.annotation.tiger.impl;

import java.lang.reflect.Method;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.NoPersistentProperty;
import org.seasar.dao.annotation.tiger.PersistentProperty;
import org.seasar.dao.annotation.tiger.Procedure;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;
import org.seasar.dao.annotation.tiger.Sqls;
import org.seasar.dao.impl.FieldDaoAnnotationReader;
import org.seasar.dao.util.ImplementInterfaceWalker;
import org.seasar.dao.util.ImplementInterfaceWalker.Status;
import org.seasar.framework.beans.BeanDesc;

/**
 * 
 * @author keizou
 * @author manhole
 * @author azusa
 */
public class DaoAnnotationReaderImpl extends FieldDaoAnnotationReader {

    private Class daoClass_;

    public DaoAnnotationReaderImpl(BeanDesc daoBeanDesc) {
        super(daoBeanDesc);
        daoClass_ = daoBeanDesc.getBeanClass();
    }

    public String getQuery(Method method) {
        Query query = method.getAnnotation(Query.class);
        return (query != null) ? query.value() : super.getQuery(method);
    }

    public String getStoredProcedureName(Method method) {
        Procedure procedure = method.getAnnotation(Procedure.class);
        return (procedure != null) ? procedure.value() : super
                .getStoredProcedureName(method);
    }

    public String[] getArgNames(Method method) {
        Arguments arg = method.getAnnotation(Arguments.class);
        return (arg != null) ? arg.value() : super.getArgNames(method);
    }

    public Class getBeanClass() {
        Class ret = getBeanClass0(daoClass_);
        return ret != null ? ret : super.getBeanClass();
    }

    @SuppressWarnings("unchecked")
    private static Class getBeanClassFromDao(Class daoClass) {
        if (daoClass.isAnnotationPresent(S2Dao.class)) {
            S2Dao s2dao = (S2Dao) daoClass.getAnnotation(S2Dao.class);
            return s2dao.bean();
        }
        return null;
    }

    private Class getBeanClass0(Class daoClass) {
        final Class beanClass = getBeanClassFromDao(daoClass);
        if (beanClass != null) {
            return beanClass;
        }

        HandlerImpl handlerImpl = new HandlerImpl();
        ImplementInterfaceWalker.walk(daoClass, handlerImpl);
        return handlerImpl.foundBeanClass;
    }

    private static class HandlerImpl implements
            ImplementInterfaceWalker.Handler {

        Class foundBeanClass;

        public Status accept(Class ifs) {
            final Class beanClass = getBeanClassFromDao(ifs);
            if (beanClass != null) {
                foundBeanClass = beanClass;
                return ImplementInterfaceWalker.BREAK;
            }
            return ImplementInterfaceWalker.CONTINUE;
        }
    }

    public String[] getNoPersistentProps(Method method) {
        NoPersistentProperty npp = method
                .getAnnotation(NoPersistentProperty.class);
        return (npp != null) ? npp.value() : super.getNoPersistentProps(method);
    }

    public String[] getPersistentProps(Method method) {
        PersistentProperty pp = (PersistentProperty) method
                .getAnnotation(PersistentProperty.class);
        return (pp != null) ? pp.value() : super.getPersistentProps(method);
    }

    public String getSQL(Method method, String suffix) {
        Sql sql = getSqls(method, suffix);
        if (sql == null) {
            sql = method.getAnnotation(Sql.class);
            if (sql == null) {
                return super.getSQL(method, suffix);
            } else if (("_" + sql.dbms()).equals(suffix)
                    || sql.dbms().equals("")) {
                return (sql != null) ? sql.value() : null;
            } else {
                return null;
            }
        }
        return (sql != null) ? sql.value() : null;
    }

    protected Sql getSqls(Method method, String dbmsSuffix) {
        Sqls sqls = method.getAnnotation(Sqls.class);
        if (sqls == null || sqls.value().length == 0) {
            return null;
        }
        Sql defaultSql = null;
        for (int i = 0; i < sqls.value().length; i++) {
            Sql sql = sqls.value()[i];
            if (dbmsSuffix.equals("_" + sql.dbms())) {
                return sql;
            }
            if ("".equals(sql.dbms())) {
                defaultSql = sql;
            }
        }

        return defaultSql;
    }

}
