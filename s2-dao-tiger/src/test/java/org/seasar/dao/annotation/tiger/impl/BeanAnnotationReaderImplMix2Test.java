/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.util.Date;

import junit.framework.AssertionFailedError;

import org.seasar.dao.BeanAnnotationReader;
import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Column;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;
import org.seasar.dao.annotation.tiger.Ids;
import org.seasar.dao.annotation.tiger.Relation;
import org.seasar.dao.annotation.tiger.ValueType;
import org.seasar.dao.impl.AbstractBeanAnnotationReaderTest;
import org.seasar.dao.impl.Department;

/**
 * @author azusa
 */
public class BeanAnnotationReaderImplMix2Test extends
        AbstractBeanAnnotationReaderTest {

    protected BeanAnnotationReader createBeanAnnotationReader(Class clazz) {
        return new BeanAnnotationReaderImpl(clazz);
    }

    protected Class getBeanClass(String className) {
        if ("AnnotationTestBean1".equals(className)) {
            return AnnotationTestBean1.class;
        } else if ("AnnotationTestBean2".equals(className)) {
            return AnnotationTestBean2.class;
        } else if ("AnnotationTestBean3".equals(className)) {
            return AnnotationTestBean3.class;
        } else if ("AnnotationTestBean4".equals(className)) {
            return AnnotationTestBean4.class;
        } else if ("AnnotationTestBean5".equals(className)) {
            return AnnotationTestBean5.class;
        } else if ("AnnotationTestBean6".equals(className)) {
            return AnnotationTestBean6.class;
        }

        throw new AssertionFailedError(className);
    }

    @Bean(table = "TABLE", noPersistentProperty = "prop2", timeStampProperty = "myTimestamp", versionNoProperty = "myVersionNo")
    public static class AnnotationTestBean1 {

        public static String TABLE = "TABLE2";

        public static final String NO_PERSISTENT_PROPS = "prop1";

        public static final String TIMESTAMP_PROPERTY = "myTimestamp1";

        public static final String VERSION_NO_PROPERTY = "myVersionNo1";

        public static final String prop1_ID = "identrity";

        public static String prop1_COLUMN = "CCprop1";

        private Department department;

        private Date myTimestamp;

        public int getProp1() {
            return 0;
        }

        @Id(value = IdType.SEQUENCE, sequenceName = "myseq")
        @Column("Cprop1")
        public void setProp1(int i) {
        }

        public int getProp2() {
            return 0;
        }

        public void setProp2(int i) {
        }

        public Date getMyTimestamp() {
            return myTimestamp;
        }

        public void setMyTimestamp(Date myTimestamp) {
            this.myTimestamp = myTimestamp;
        }

        public static final int department_RELNO = 1;

        public static final String department_RELKEYS = "DEPTNUM:DEPT";

        public Department getDepartment() {
            return department;
        }

        @Relation(relationNo = 0, relationKey = "DEPTNUM:DEPTNO")
        public void setDepartment(Department department) {
            this.department = department;
        }

    }

    public static class AnnotationTestBean2 {

        public static String prop1_COLUMN = "CCprop1";

        public int getProp1() {
            return 0;
        }

        @Column("Cprop1")
        public void setProp1(int i) {
        }

        public int getProp2() {
            return 0;
        }

        public void setProp2(int i) {
        }
    }

    public static class AnnotationTestBean3 {

        private String aaa;

        private String bbb;

        public static String bbb_VALUE_TYPE = "hogeType";

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public String getBbb() {
            return bbb;
        }

        @ValueType("fooType")
        public void setBbb(String bbb) {
            this.bbb = bbb;
        }
    }

    public static class AnnotationTestBean4 {

        private String aaa;

        private String bbb;

        public static final String aaa_oracle_ID = "sequence, sequenceName=myseq";

        public static final String aaa_mysql_ID = "identity";

        public static final String aaa_ID = "identity";

        @Ids( {
                @Id(value = IdType.IDENTITY, dbms = "oracle"),
                @Id(value = IdType.SEQUENCE, sequenceName = "myseq", dbms = "mysql"),
                @Id(value = IdType.SEQUENCE, sequenceName = "myseq_2", allocationSize = 10) })
        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public String getBbb() {
            return bbb;
        }

        @ValueType("fooType")
        public void setBbb(String bbb) {
            this.bbb = bbb;
        }
    }

    public static class AnnotationTestBean5 {

        private String aaa;

        private String bbb;

        public static final String aaa_oracle_ID = "sequence, sequenceName=myseq";

        @Id(value = IdType.IDENTITY, dbms = "oracle")
        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public String getBbb() {
            return bbb;
        }

        @ValueType("fooType")
        public void setBbb(String bbb) {
            this.bbb = bbb;
        }
    }

    public static class AnnotationTestBean6 {

        private String aaa;

        private String bbb;

        public static final String aaa_ID = "sequence, sequenceName=myseq";

        @Id(value = IdType.IDENTITY)
        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public String getBbb() {
            return bbb;
        }

        @ValueType("fooType")
        public void setBbb(String bbb) {
            this.bbb = bbb;
        }
    }

}
