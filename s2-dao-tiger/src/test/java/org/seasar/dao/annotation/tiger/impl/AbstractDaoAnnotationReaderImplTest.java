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

import junit.framework.TestCase;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author manhole
 * @author azusa
 */
public abstract class AbstractDaoAnnotationReaderImplTest extends TestCase {

    protected DaoAnnotationReaderImpl annotationReader;

    protected Class clazz;

    protected Class aaaClazz;

    protected Class daoClazz;

    public void testBasic() throws Exception {
        final BeanDesc daoDesc = BeanDescFactory.getBeanDesc(clazz);
        DaoAnnotationReaderImpl reader = new DaoAnnotationReaderImpl(daoDesc);
        assertEquals(aaaClazz, reader.getBeanClass());

        String query = reader.getQuery(daoClazz.getMethod("getAaaById2",
                new Class[] { int.class }));
        assertEquals("A > B", query);
    }

    public void testBeanClass() throws Exception {
        assertEquals(aaaClazz, annotationReader.getBeanClass());
    }

    public void testQuery() throws Exception {
        String query = annotationReader.getQuery(clazz.getMethod("getAaaById2",
                new Class[] { int.class }));
        assertEquals("A > B", query);
    }

    public void testSql() throws Exception {
        String sql = annotationReader.getSQL(clazz.getMethod("getAaaById3",
                new Class[] { int.class }), null);
        assertEquals("SELECT * FROM AAA", sql);
    }

    public void testArgNames() throws Exception {
        String[] argNames = annotationReader.getArgNames(clazz.getMethod(
                "getAaaById1", new Class[] { int.class }));
        assertEquals(2, argNames.length);
        assertEquals("aaa1", argNames[0]);
        assertEquals("aaa2", argNames[1]);
    }

    public void testNoPersistentProps() throws Exception {
        final String[] noPersistentProps = annotationReader
                .getNoPersistentProps(clazz.getMethod("createAaa1",
                        new Class[] { aaaClazz }));
        assertEquals(1, noPersistentProps.length);
        assertEquals("abc", noPersistentProps[0]);
    }

    public void testPersistentProps() throws Exception {
        final String[] persistentProps = annotationReader
                .getPersistentProps(clazz.getMethod("createAaa2",
                        new Class[] { aaaClazz }));
        assertEquals(1, persistentProps.length);
        assertEquals("def", persistentProps[0]);
    }

}
