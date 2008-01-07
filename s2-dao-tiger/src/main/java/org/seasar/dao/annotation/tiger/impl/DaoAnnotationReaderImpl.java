/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.CheckSingleRowUpdate;
import org.seasar.dao.annotation.tiger.NoPersistentProperty;
import org.seasar.dao.annotation.tiger.PersistentProperty;
import org.seasar.dao.annotation.tiger.Procedure;
import org.seasar.dao.annotation.tiger.ProcedureCall;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;
import org.seasar.dao.annotation.tiger.SqlFile;
import org.seasar.dao.annotation.tiger.Sqls;
import org.seasar.dao.annotation.tiger.util.AnnotationUtil;
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

    private Class<?> daoClass_;

    public DaoAnnotationReaderImpl(BeanDesc daoBeanDesc) {
        super(daoBeanDesc);
        daoClass_ = daoBeanDesc.getBeanClass();
    }

    @Override
    public String getQuery(Method method) {
        Query query = method.getAnnotation(Query.class);
        return (query != null) ? query.value() : super.getQuery(method);
    }

    @Override
    public String getStoredProcedureName(Method method) {
        Procedure procedure = method.getAnnotation(Procedure.class);
        return (procedure != null) ? procedure.value() : super
                .getStoredProcedureName(method);
    }

    @Override
    public String getProcedureCallName(Method method) {
        ProcedureCall procedureCall = method.getAnnotation(ProcedureCall.class);
        return (procedureCall != null) ? procedureCall.value() : super
                .getProcedureCallName(method);
    }

    @Override
    public String[] getArgNames(Method method) {
        Arguments arg = method.getAnnotation(Arguments.class);
        return (arg != null) ? arg.value() : super.getArgNames(method);
    }

    @Override
    public Class<?> getBeanClass() {
        Class<?> ret = getBeanClass0(daoClass_);
        return ret != null ? ret : super.getBeanClass();
    }

    @Override
    public Class<?> getBeanClass(Method method) {
        Type type = method.getGenericReturnType();
        if (isTypeOf(type, List.class)) {
            Type ret = getElementTypeOfList(type);
            if (ret != null) {
                return getRawClass(ret);
            }
        }
        return super.getBeanClass(method);
    }

    protected static Type getElementTypeOfList(final Type type) {
        if (!isTypeOf(type, List.class)) {
            return null;
        }
        return getGenericParameter(type, 0);
    }

    protected static boolean isTypeOf(final Type type, final Class<?> clazz) {
        if (Class.class.isInstance(type)) {
            return clazz.isAssignableFrom(Class.class.cast(type));
        }
        if (ParameterizedType.class.isInstance(type)) {
            final ParameterizedType parameterizedType = ParameterizedType.class
                    .cast(type);
            return isTypeOf(parameterizedType.getRawType(), clazz);
        }
        return false;
    }

    protected static Type[] getGenericParameter(final Type type) {
        if (!ParameterizedType.class.isInstance(type)) {
            return null;
        }
        return ParameterizedType.class.cast(type).getActualTypeArguments();
    }

    protected static Type getGenericParameter(final Type type, final int index) {
        if (!ParameterizedType.class.isInstance(type)) {
            return null;
        }
        final Type[] genericParameter = getGenericParameter(type);
        if (genericParameter == null) {
            return null;
        }
        return genericParameter[index];
    }

    /**
     * <code>type</code>の原型を返します。
     * <p>
     * <code>type</code>が原型でもパラメータ化された型でもない場合は<code>null</code>を返します。
     * </p>
     * 
     * @param type
     *            タイプ
     * @return <code>type</code>の原型
     */
    protected static Class<?> getRawClass(final Type type) {
        if (Class.class.isInstance(type)) {
            return Class.class.cast(type);
        }
        if (ParameterizedType.class.isInstance(type)) {
            final ParameterizedType parameterizedType = ParameterizedType.class
                    .cast(type);
            return getRawClass(parameterizedType.getRawType());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static Class getBeanClassFromDao(Class daoClass) {
        if (daoClass.isAnnotationPresent(S2Dao.class)) {
            S2Dao s2dao = (S2Dao) daoClass.getAnnotation(S2Dao.class);
            return s2dao.bean();
        }
        return null;
    }

    private Class<?> getBeanClass0(Class<?> daoClass) {
        final Class<?> beanClass = getBeanClassFromDao(daoClass);
        if (beanClass != null) {
            return beanClass;
        }

        HandlerImpl handlerImpl = new HandlerImpl();
        ImplementInterfaceWalker.walk(daoClass, handlerImpl);
        return handlerImpl.foundBeanClass;
    }

    private static class HandlerImpl implements
            ImplementInterfaceWalker.Handler {

        Class<?> foundBeanClass;

        @SuppressWarnings("unchecked")
        public Status accept(Class ifs) {
            final Class<?> beanClass = getBeanClassFromDao(ifs);
            if (beanClass != null) {
                foundBeanClass = beanClass;
                return ImplementInterfaceWalker.BREAK;
            }
            return ImplementInterfaceWalker.CONTINUE;
        }
    }

    @Override
    public String[] getNoPersistentProps(Method method) {
        NoPersistentProperty npp = method
                .getAnnotation(NoPersistentProperty.class);
        return (npp != null) ? npp.value() : super.getNoPersistentProps(method);
    }

    @Override
    public String[] getPersistentProps(Method method) {
        PersistentProperty pp = (PersistentProperty) method
                .getAnnotation(PersistentProperty.class);
        return (pp != null) ? pp.value() : super.getPersistentProps(method);
    }

    @Override
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

    @Override
    public boolean isSqlFile(final Method method) {
        final SqlFile sqlFile = method.getAnnotation(SqlFile.class);
        if (sqlFile != null) {
            return true;
        }
        return super.isSqlFile(method);
    }

    @Override
    public String getSqlFilePath(Method method) {
        final SqlFile sqlFile = method.getAnnotation(SqlFile.class);
        if (sqlFile != null) {
            String path = sqlFile.value();
            if (path == null) {
                path = "";
            }
            return path;
        }
        return super.getSqlFilePath(method);
    }

    @Override
    public boolean isCheckSingleRowUpdate() {
        final CheckSingleRowUpdate checkSingleRowUpdate = AnnotationUtil
                .getAnnotation(daoClass_, CheckSingleRowUpdate.class);
        if (checkSingleRowUpdate != null) {
            return checkSingleRowUpdate.value();
        }
        return super.isCheckSingleRowUpdate();
    }

    @Override
    public boolean isCheckSingleRowUpdate(Method method) {
        final CheckSingleRowUpdate checkSingleRowUpdate = method
                .getAnnotation(CheckSingleRowUpdate.class);
        if (checkSingleRowUpdate != null) {
            return checkSingleRowUpdate.value();
        }
        return super.isCheckSingleRowUpdate(method);
    }

}
