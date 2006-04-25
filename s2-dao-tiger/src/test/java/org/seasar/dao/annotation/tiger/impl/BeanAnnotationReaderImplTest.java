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

import org.seasar.dao.BeanAnnotationReader;
import org.seasar.dao.annotation.tiger.ValueType;
import org.seasar.extension.jdbc.types.BigDecimalType;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author manhole
 */
public class BeanAnnotationReaderImplTest extends S2TestCase {

    public void testGetValueType() throws Exception {
        Class clazz = AnnotationTestBean3.class;
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
        BeanAnnotationReader annotationReader = new BeanAnnotationReaderImpl(
            clazz);
        PropertyDesc aaaPd = beanDesc.getPropertyDesc("aaa");
        assertEquals(null, annotationReader.getValueType(aaaPd));

        PropertyDesc bbbPd = beanDesc.getPropertyDesc("bbb");
        assertEquals(BigDecimalType.class, annotationReader.getValueType(bbbPd));
    }

    public static class AnnotationTestBean3 {

        private String aaa;

        private String bbb;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public String getBbb() {
            return bbb;
        }

        @ValueType(BigDecimalType.class)
        public void setBbb(String bbb) {
            this.bbb = bbb;
        }
    }

}
