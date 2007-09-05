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
package org.seasar.dao.unit;

import java.util.ArrayList;
import java.util.List;

import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.dataset.impl.DataSetImpl;
import org.seasar.extension.unit.S2TestCase;

/**
 * @author taedium
 * 
 */
public class S2DaoAssertTest extends S2TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("dao.dicon");
    }

    public void testAssertBeanEquals_DataSet_Object() {
        DataSet expected = new DataSetImpl();
        DataTable table = expected.addTable("emp");
        table.addColumn("aaa");
        table.addColumn("bbb_0");
        DataRow row = table.addRow();
        row.setValue("aaa", "111");
        row.setValue("bbb_0", "222");
        Hoge bean = new Hoge();
        bean.setAaa("111");
        Foo foo = new Foo();
        foo.setBbb("222");
        bean.setFoo(foo);
        S2DaoAssert.assertBeanEquals(expected, bean);
    }

    public void testAssertBeanEquals_DataSet_List() {
        DataSet expected = new DataSetImpl();
        DataTable table = expected.addTable("emp");
        table.addColumn("aaa");
        table.addColumn("bbb_0");
        DataRow row = table.addRow();
        row.setValue("aaa", "111");
        row.setValue("bbb_0", "222");
        Hoge bean = new Hoge();
        bean.setAaa("111");
        Foo foo = new Foo();
        foo.setBbb("222");
        bean.setFoo(foo);
        List<Hoge> list = new ArrayList<Hoge>();
        list.add(bean);
        S2DaoAssert.assertBeanEquals(expected, list);
    }

    public static class Hoge {

        public static final int foo_RELNO = 0;

        public static final String aaa_ID = "assigned";

        private String aaa;

        private Foo foo;

        /**
         * @return Returns the aaa.
         */
        public String getAaa() {
            return aaa;
        }

        /**
         * @param aaa
         *            The aaa to set.
         */
        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        /**
         * @return Returns the foo.
         */
        public Foo getFoo() {
            return foo;
        }

        /**
         * @param foo
         *            The foo to set.
         */
        public void setFoo(Foo foo) {
            this.foo = foo;
        }
    }

    public static class Foo {

        public static final String bbb_ID = "assigned";

        private String bbb;

        /**
         * @return Returns the bbb.
         */
        public String getBbb() {
            return bbb;
        }

        /**
         * @param bbb
         *            The bbb to set.
         */
        public void setBbb(String bbb) {
            this.bbb = bbb;
        }
    }

}
