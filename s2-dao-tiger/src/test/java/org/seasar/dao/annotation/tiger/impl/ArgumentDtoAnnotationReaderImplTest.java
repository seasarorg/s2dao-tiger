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

import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.seasar.dao.ArgumentDtoAnnotationReader;
import org.seasar.dao.annotation.tiger.ParameterType;
import org.seasar.dao.annotation.tiger.ProcedureParameter;
import org.seasar.dao.annotation.tiger.ValueType;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author taedium
 * 
 */
public class ArgumentDtoAnnotationReaderImplTest extends TestCase {

    private ArgumentDtoAnnotationReader reader = new ArgumentDtoAnnotationReaderImpl();

    private BeanDesc hogeDesc = BeanDescFactory.getBeanDesc(Hoge.class);

    private BeanDesc fooDesc = BeanDescFactory.getBeanDesc(Foo.class);

    public void testGetProcedureParameter_fieldAnnotation() throws Exception {
        Field field = hogeDesc.getField("aaa");
        String value = reader.getProcedureParameter(hogeDesc, field);
        assertEquals("in", value);
    }

    public void testGetProcedureParameter_methodAnnoation() throws Exception {
        Field field = hogeDesc.getField("ddd");
        String value = reader.getProcedureParameter(hogeDesc, field);
        assertEquals("out", value);
    }

    public void testGetProcedureParameter_constantAnnotation() throws Exception {
        Field field = hogeDesc.getField("aaa");
        String value = reader.getProcedureParameter(fooDesc, field);
        assertEquals("in", value);
    }

    public void testGetProcedureParameter_none() throws Exception {
        Field field = hogeDesc.getField("bbb");
        String value = reader.getProcedureParameter(hogeDesc, field);
        assertNull(value);
    }

    public void testGetProcedureParameter_none_constantAnnotation()
            throws Exception {
        Field field = hogeDesc.getField("bbb");
        String value = reader.getProcedureParameter(fooDesc, field);
        assertNull(value);
    }

    public void testGetProcedureParameter_public_fieldAnnotation()
            throws Exception {
        Field field = hogeDesc.getField("ccc");
        String value = reader.getProcedureParameter(hogeDesc, field);
        assertEquals("out", value);
    }

    public void testGetProcedureParameter_public_constantAnnotation()
            throws Exception {
        Field field = hogeDesc.getField("ccc");
        String value = reader.getProcedureParameter(fooDesc, field);
        assertEquals("out", value);
    }

    public void testGetValueType_fieldAnnotation() throws Exception {
        Field field = hogeDesc.getField("aaa");
        assertEquals("hogeValueType", reader.getValueType(hogeDesc, field));
    }

    public void testGetValueType_methodAnnotation() throws Exception {
        Field field = hogeDesc.getField("ddd");
        assertEquals("barValueType", reader.getValueType(hogeDesc, field));
    }

    public void testGetValueType_constantAnnotation() throws Exception {
        Field field = hogeDesc.getField("aaa");
        assertEquals("hogeValueType", reader.getValueType(fooDesc, field));
    }

    public static class Hoge {

        @ValueType("hogeValueType")
        @ProcedureParameter()
        @SuppressWarnings("unused")
        private String aaa;

        @SuppressWarnings("unused")
        private String bbb;

        @ProcedureParameter(ParameterType.OUT)
        public String ccc;

        private String ddd;

        @ProcedureParameter(ParameterType.OUT)
        public void setDdd(String ddd) {
            this.ddd = ddd;
        }

        @ValueType("barValueType")
        public String getDdd() {
            return ddd;
        }
    }

    public static class Foo {

        public static final String PROCEDURE_PARAMETERS = null;

        public static final String aaa_VALUE_TYPE = "hogeValueType";

        public static final String aaa_PROCEDURE_PARAMETER = "in";

        public static final String ccc_PROCEDURE_PARAMETER = "out";

        @SuppressWarnings("unused")
        private String aaa;

        @SuppressWarnings("unused")
        private String bbb;

        @SuppressWarnings("unused")
        public String ccc;
    }
}
