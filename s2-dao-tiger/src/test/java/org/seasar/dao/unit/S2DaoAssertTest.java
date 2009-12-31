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
package org.seasar.dao.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 
     * @throws Exception
     */
    public void testAssertMapEquals_DataSet_Map() throws Exception {
        DataSet dataSet = new DataSetImpl();
        DataTable table = dataSet.addTable("hoge");
        table.addColumn("aaa");
        table.addColumn("bbb");
        DataRow row = table.addRow();
        row.setValue("aaa", 10);
        row.setValue("bbb", 20);

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("aaa", 10);
        map.put("bbb", 20);

        S2DaoAssert.assertMapEquals(dataSet, map);
    }

    /**
     * 
     * @throws Exception
     */
    public void testAssertMapEquals_DataSet_List() throws Exception {
        DataSet dataSet = new DataSetImpl();
        DataTable table = dataSet.addTable("hoge");
        table.addColumn("aaa");
        table.addColumn("bbb");
        DataRow row = table.addRow();
        row.setValue("aaa", 10);
        row.setValue("bbb", 20);
        row = table.addRow();
        row.setValue("aaa", 30);
        row.setValue("bbb", 40);

        List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("aaa", 10);
        map.put("bbb", 20);
        list.add(map);
        map = new HashMap<String, Integer>();
        map.put("aaa", 30);
        map.put("bbb", 40);
        list.add(map);

        S2DaoAssert.assertMapEquals(dataSet, list);
    }

    public static class Hoge {

        public static final int foo_RELNO = 0;

        public static final String aaa_ID = "assigned";

        private String aaa;

        private Foo foo;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public Foo getFoo() {
            return foo;
        }

        public void setFoo(Foo foo) {
            this.foo = foo;
        }
    }

    public static class Foo {

        public static final String bbb_ID = "assigned";

        private String bbb;

        public String getBbb() {
            return bbb;
        }

        public void setBbb(String bbb) {
            this.bbb = bbb;
        }
    }

}
