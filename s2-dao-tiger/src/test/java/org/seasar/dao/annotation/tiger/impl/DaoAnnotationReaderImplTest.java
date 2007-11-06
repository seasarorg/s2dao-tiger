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
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.seasar.dao.DaoMetaDataFactory;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.CheckSingleRowUpdate;
import org.seasar.dao.annotation.tiger.NoPersistentProperty;
import org.seasar.dao.annotation.tiger.PersistentProperty;
import org.seasar.dao.annotation.tiger.ProcedureCall;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;
import org.seasar.dao.annotation.tiger.SqlFile;
import org.seasar.dao.annotation.tiger.Sqls;
import org.seasar.dao.impl.AbstractDao;
import org.seasar.dao.impl.AbstractDaoAnnotationReaderImplTest;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author azusa
 * 
 */
public class DaoAnnotationReaderImplTest extends
        AbstractDaoAnnotationReaderImplTest {

    protected void setUp() throws Exception {
        clazz = AbstractAaaDaoImpl.class;
        BeanDesc daoDesc = BeanDescFactory.getBeanDesc(clazz);
        annotationReader = new DaoAnnotationReaderImpl(daoDesc);
        aaaClazz = Aaa.class;
        daoClazz = AaaDao.class;
    }

    public void testGetElementTypeOfList() throws Exception {
        Method method = Aaa2Dao.class.getMethod("findAll", new Class[0]);
        Type type = method.getGenericReturnType();
        Type ret = DaoAnnotationReaderImpl.getElementTypeOfList(type);
        assertEquals(Aaa.class, ret);
    }

    public void testGetBeanClass() throws Exception {
        Method method = Aaa2Dao.class.getMethod("findAll", new Class[0]);
        assertEquals(Aaa.class, annotationReader.getBeanClass(method));
    }

    public void testGetBeanClassGenerics() throws Exception {
        Method method = AaaDao.class.getMethod("findAll2", new Class[0]);
        Class<?> clazz = annotationReader.getBeanClass(method);
        assertEquals(Map.class, clazz);
    }

    @S2Dao(bean = Aaa.class)
    @CheckSingleRowUpdate(false)
    public static interface AaaDao {

        @Arguments( { "aaa1", "aaa2" })
        public Aaa getAaaById1(int id);

        @Query("A > B")
        public Aaa getAaaById2(int id);

        @Sql("SELECT * FROM AAA")
        public Aaa getAaaById3(int id);

        @Sql("SELECT * FROM AAA")
        public List<Aaa> findAll();

        public List<Map<String, String>> findAll2();

        public Aaa[] findArray();

        public Aaa find(int id);

        @NoPersistentProperty("abc")
        public Aaa createAaa1(Aaa aaa);

        @PersistentProperty("def")
        public Aaa createAaa2(Aaa aaa);

        @CheckSingleRowUpdate(false)
        public int createAaa3(Aaa aaa);

        @Sqls( { @Sql(value = "SELECT * FROM BBB", dbms = "oracle"),
                @Sql("SELECT * FROM DDD") })
        public Aaa selectB(int id);

        @Sql(value = "SELECT * FROM CCC", dbms = "oracle")
        public Aaa selectC(int id);

        @SqlFile
        public Aaa findUsingSqlFile(int id);

        @SqlFile("org/seasar/dao/impl/sqlfile/testFile.sql")
        public Aaa findUsingSqlFile2(int id);

        @ProcedureCall("hoge")
        public void execute();

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
