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
package org.seasar.dao.tiger.impl;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.seasar.dao.BeanMetaData;
import org.seasar.dao.DtoMetaData;
import org.seasar.dao.DtoMetaDataFactory;
import org.seasar.dao.RelationRowCreator;
import org.seasar.dao.RowCreator;
import org.seasar.dao.impl.RelationRowCreatorImpl;
import org.seasar.dao.impl.RowCreatorImpl;
import org.seasar.dao.pager.PagerContext;
import org.seasar.dao.tiger.FetchHandler;
import org.seasar.extension.jdbc.ResultSetHandler;

/**
 * @author jundu
 * 
 */
public class FetchResultSetHandler implements ResultSetHandler {

    protected Class<?> beanClass;

    protected BeanMetaData beanMetaData;

    protected DtoMetaDataFactory dtoMetaDataFactory;

    public FetchResultSetHandler(final Class<?> beanClass,
            final BeanMetaData beanMetaData,
            final DtoMetaDataFactory dtoMetaDataFactory) {
        this.beanClass = beanClass;
        this.beanMetaData = beanMetaData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seasar.extension.jdbc.ResultSetHandler#handle(java.sql.ResultSet)
     */
    public Object handle(ResultSet resultSet) throws SQLException {
        FetchHandler<?> fetchHandler = getFetchHandler();
        Class<?> parameterClass = getParameterClass(fetchHandler);
        ResultSetHandler resultSetHandler = null;
        if (parameterClass.isAssignableFrom(beanClass)) {
            resultSetHandler = new FetchBeanMetaDataResultSetHandler(
                    beanMetaData, createRowCreator(),
                    createRelationRowCreator(), fetchHandler);
        } else {
            final DtoMetaData dtoMetaData = dtoMetaDataFactory
                    .getDtoMetaData(parameterClass);
            resultSetHandler = new FetchDtoMetaDataResultSetHandler(
                    dtoMetaData, createRowCreator(), fetchHandler);
        }
        return resultSetHandler.handle(resultSet);
    }

    /**
     * @param fetchHandler
     */
    protected Class<?> getParameterClass(FetchHandler<?> fetchHandler) {
        Class<?> clazz = null;
        Method[] methods = fetchHandler.getClass().getMethods();
        for (Method method : methods) {
            // executeというメソッド名でパラメータが１つ、Objectクラスではないもの
            if (method.getName().equals("execute")) {
                Class<?>[] c = method.getParameterTypes();
                if (c.length == 1 && !(c[0].equals(Object.class))) {
                    clazz = c[0];
                    break;
                }
            }
        }
        if (clazz == null) {
            throw new IllegalArgumentException(
                    "FetchHandler#execute is not found. (or argument is Object)");
        }
        return clazz;
    }

    /**
     * 
     */
    protected FetchHandler<?> getFetchHandler() {
        Object[] args = PagerContext.getContext().peekArgs();
        if (args.length < 1 || !(args[args.length - 1] instanceof FetchHandler)) {
            throw new IllegalArgumentException(
                    "Last argument is not FetchHandler.");
        }
        return (FetchHandler<?>) args[args.length - 1];
    }

    protected RowCreator createRowCreator() {
        return new RowCreatorImpl();
    }

    protected RelationRowCreator createRelationRowCreator() {
        return new RelationRowCreatorImpl();
    }

}
