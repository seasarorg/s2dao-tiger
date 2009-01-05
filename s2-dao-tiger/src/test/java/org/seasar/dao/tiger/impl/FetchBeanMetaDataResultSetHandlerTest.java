/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.dao.tiger.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.seasar.dao.BeanMetaData;
import org.seasar.dao.DaoMetaData;
import org.seasar.dao.RelationRowCreator;
import org.seasar.dao.RowCreator;
import org.seasar.dao.annotation.tiger.impl.AnnotationReaderFactoryImpl;
import org.seasar.dao.impl.Employee;
import org.seasar.dao.impl.RelationRowCreatorImpl;
import org.seasar.dao.impl.RowCreatorImpl;
import org.seasar.dao.impl.SelectDynamicCommand;
import org.seasar.dao.pager.PagerContext;
import org.seasar.dao.tiger.FetchHandler;
import org.seasar.dao.unit.S2DaoTestCase;
import org.seasar.extension.jdbc.ResultSetHandler;

/**
 * @author jundu
 * 
 */
public class FetchBeanMetaDataResultSetHandlerTest extends S2DaoTestCase {

    private BeanMetaData beanMetaData;

    public void testHandle() throws Exception {
        String sql = "select * from emp";
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        final List<Employee> ret = new ArrayList<Employee>();
        ResultSetHandler handler = new FetchBeanMetaDataResultSetHandler(
                beanMetaData, createRowCreator(), createRelationRowCreator(),
                new FetchHandler<Employee>() {
                    public boolean execute(Employee bean) {
                        ret.add(bean);
                        return true;
                    }
                });
        try {
            ResultSet rs = ps.executeQuery();
            try {
                handler.handle(rs);
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
        assertNotNull("1", ret);
        for (int i = 0; i < ret.size(); ++i) {
            Employee emp = (Employee) ret.get(i);
            System.out.println(emp.getEmpno() + "," + emp.getEname());
        }
    }

    public void testHandle2() {
        try {
            PagerContext.start();
            DaoMetaData dmd = createDaoMetaData(EmployeeDao.class);
            assertNotNull("1", dmd);
            SelectDynamicCommand cmd = (SelectDynamicCommand) dmd
                    .getSqlCommand("fetchEmployeesBySearchCondition");
            assertNotNull("2", cmd);
            final List<Employee> ret = new ArrayList<Employee>();
            FetchHandler<Employee> handler = new FetchHandler<Employee>() {
                public boolean execute(Employee emp) {
                    if (!emp.getDepartment().getDname().equals("RESEARCH")) {
                        fail();
                    }
                    ret.add(emp);
                    return true;
                }
            };
            EmployeeSearchCondition dto = new EmployeeSearchCondition();
            dto.setDname("RESEARCH");
            Object[] args = new Object[] { dto, handler };
            PagerContext.getContext().pushArgs(args);
            Object count = cmd.execute(args);
            assertNotNull("3", count);
            assertEquals("4", Integer.valueOf(5), count);
        } finally {
            PagerContext.getContext().popArgs();
            PagerContext.end();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        include("j2ee.dicon");
        setAnnotationReaderFactory(new AnnotationReaderFactoryImpl());
    }

    protected void setUpAfterBindFields() throws Throwable {
        super.setUpAfterBindFields();
        beanMetaData = createBeanMetaData(Employee.class);
    }

    protected RowCreator createRowCreator() {// [DAO-118] (2007/08/25)
        return new RowCreatorImpl();
    }

    protected RelationRowCreator createRelationRowCreator() {
        return new RelationRowCreatorImpl();
    }

}
