/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

/**
 * @author manhole
 * @author azusa
 */
public class BeanAnnotationReaderImplPublicFieldTest extends
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

        @Relation(relationNo = 0, relationKey = "DEPTNUM:DEPTNO")
        public Department department;

        public Date myTimestamp;

        @Id(value = IdType.SEQUENCE, sequenceName = "myseq")
        @Column("Cprop1")
        public int prop1;

        public int prop2;

    }

    public static class AnnotationTestBean2 {

        @Column("Cprop1")
        public int pror1;

        public int prop2;

    }

    public static class AnnotationTestBean3 {

        public String aaa;

        @ValueType("fooType")
        public String bbb;
    }

    public static class AnnotationTestBean4 {

        @Ids( {
                @Id(value = IdType.IDENTITY, dbms = "oracle"),
                @Id(value = IdType.SEQUENCE, sequenceName = "myseq", dbms = "mysql"),
                @Id(value = IdType.SEQUENCE, sequenceName = "myseq_2", allocationSize = 10) })
        public String aaa;

        @ValueType("fooType")
        public String bbb;

    }

    public static class AnnotationTestBean5 {

        @Id(value = IdType.IDENTITY, dbms = "oracle")
        public String aaa;

        @ValueType("fooType")
        public String bbb;
    }

    public static class AnnotationTestBean6 {

        @Id(value = IdType.IDENTITY)
        public String aaa;

        @ValueType("fooType")
        public String bbb;


    }

}
