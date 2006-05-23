/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import org.seasar.dao.DaoMetaDataFactory;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.NoPersistentProperty;
import org.seasar.dao.annotation.tiger.PersistentProperty;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;
import org.seasar.dao.impl.AbstractDao;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author manhole
 */
public class DaoAnnotationReaderImplTest extends TestCase {

    private DaoAnnotationReaderImpl annotationReader;

    public void testBasic() throws Exception {
        final BeanDesc daoDesc = BeanDescFactory.getBeanDesc(AaaDao.class);
        DaoAnnotationReaderImpl reader = new DaoAnnotationReaderImpl(daoDesc);
        assertEquals(Aaa.class, reader.getBeanClass());
    }

    protected void setUp() throws Exception {
        super.setUp();
        BeanDesc daoDesc = BeanDescFactory
            .getBeanDesc(AbstractAaaDaoImpl.class);
        annotationReader = new DaoAnnotationReaderImpl(daoDesc);
    }

    public void testBeanClass() throws Exception {
        assertEquals(Aaa.class, annotationReader.getBeanClass());
    }

    public void testQuery() throws Exception {
        String query = annotationReader.getQuery(AbstractAaaDaoImpl.class
            .getMethod("getAaaById2", new Class[] { int.class }));
        assertEquals("A > B", query);
    }

    public void testSql() throws Exception {
        String sql = annotationReader.getSQL(AbstractAaaDaoImpl.class
            .getMethod("getAaaById3", new Class[] { int.class }), null);
        assertEquals("SELECT * FROM AAA", sql);
    }

    public void testArgNames() throws Exception {
        String[] argNames = annotationReader
            .getArgNames(AbstractAaaDaoImpl.class.getMethod("getAaaById1",
                new Class[] { int.class }));
        assertEquals(2, argNames.length);
        assertEquals("aaa1", argNames[0]);
        assertEquals("aaa2", argNames[1]);
    }

    public void testNoPersistentProps() throws Exception {
        final String[] noPersistentProps = annotationReader
            .getNoPersistentProps(AbstractAaaDaoImpl.class.getMethod(
                "createAaa1", new Class[] { Aaa.class }));
        assertEquals(1, noPersistentProps.length);
        assertEquals("abc", noPersistentProps[0]);
    }

    public void testPersistentProps() throws Exception {
        final String[] persistentProps = annotationReader
            .getPersistentProps(AbstractAaaDaoImpl.class.getMethod(
                "createAaa2", new Class[] { Aaa.class }));
        assertEquals(1, persistentProps.length);
        assertEquals("def", persistentProps[0]);
    }

    @S2Dao(bean = Aaa.class)
    public static interface AaaDao {

        @Arguments( { "aaa1", "aaa2" })
        public Aaa getAaaById1(int id);

        @Query("A > B")
        public Aaa getAaaById2(int id);

        @Sql("SELECT * FROM AAA")
        public Aaa getAaaById3(int id);

        @NoPersistentProperty("abc")
        public Aaa createAaa1(Aaa aaa);

        @PersistentProperty("def")
        public Aaa createAaa2(Aaa aaa);

    }

    public static interface Aaa2Dao extends AaaDao {
    }

    public static abstract class AbstractAaaDaoImpl extends AbstractDao
        implements Aaa2Dao {

        public AbstractAaaDaoImpl(DaoMetaDataFactory daoMetaDataFactory) {
            super(daoMetaDataFactory);
        }

    }

    public static class Aaa {
    }

}
