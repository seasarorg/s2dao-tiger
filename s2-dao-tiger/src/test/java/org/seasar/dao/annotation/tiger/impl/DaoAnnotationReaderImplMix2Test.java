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

import java.util.List;

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
 * 定数アノテーションとTigerアノテーションが混在する場合はTigerアノテーションが優先
 * 
 * @author azusa
 * 
 */
public class DaoAnnotationReaderImplMix2Test extends
        AbstractDaoAnnotationReaderImplTest {

    @Override
    protected void setUp() throws Exception {
        clazz = AbstractAaaDaoImpl.class;
        BeanDesc daoDesc = BeanDescFactory.getBeanDesc(clazz);
        annotationReader = new DaoAnnotationReaderImpl(daoDesc);
        aaaClazz = Aaa.class;
        daoClazz = AaaDao.class;
    }

    @S2Dao(bean = Aaa.class, checkSingleRowUpdate = false)
    public static interface AaaDao {

        public static final Class BEAN = Bbb.class;

        public static final boolean CHECK_SINGLE_ROW_UPDATE = true;

        public static final String getAaaById1_ARGS = "aaa1,aaa3";

        @Arguments( { "aaa1", "aaa2" })
        public Aaa getAaaById1(int id);

        public static final String getAaaById2_QUERY = "A = B";

        @Query("A > B")
        public Aaa getAaaById2(int id);

        public static final String getAaaById3_SQL = "SELECT * FROM BBB";

        @Sql("SELECT * FROM AAA")
        public Aaa getAaaById3(int id);

        public static final String createAaa1_NO_PERSISTENT_PROPS = "def";

        @NoPersistentProperty("abc")
        public Aaa createAaa1(Aaa aaa);

        public static final String createAaa2_PERSISTENT_PROPS = "ghi";

        @PersistentProperty("def")
        public Aaa createAaa2(Aaa aaa);

        public static final boolean createAaa3_CHECK_SINGLE_ROW_UPDATE = true;

        @CheckSingleRowUpdate(false)
        public int createAaa3(Aaa aaa);

        // public Class findAll_BEAN = String.class;

        public List<Aaa> findAll();

        public Aaa[] findArray();

        public Aaa find(int id);

        public static final String selectB_oracle_SQL = "SELECT * FROM EEE";

        public static final String selectB_SQL = "SELECT * FROM FFF";

        @Sqls( { @Sql(value = "SELECT * FROM BBB", dbms = "oracle"),
                @Sql("SELECT * FROM DDD") })
        public Aaa selectB(int id);

        public static final String selectC_oracle_SQL = "SELECT * FROM GGG";

        @Sql(value = "SELECT * FROM CCC", dbms = "oracle")
        public Aaa selectC(int id);

        public String findUsingSqlFile_SQL_FILE = null;

        @SqlFile
        public Aaa findUsingSqlFile(int id);

        public String findUsingSqlFile2_SQL_FILE = "noneFile.sql";

        @SqlFile("org/seasar/dao/impl/sqlfile/testFile.sql")
        public Aaa findUsingSqlFile2(int id);

        public String execute_PROCEDURE_CALL = "hoge";

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

    public static class Bbb {
    }

}
