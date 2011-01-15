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
package org.seasar.dao.tiger.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.seasar.dao.RowCreator;
import org.seasar.dao.impl.EmployeeDto;
import org.seasar.dao.impl.EmployeeDto3;
import org.seasar.dao.impl.RowCreatorImpl;
import org.seasar.dao.tiger.FetchHandler;
import org.seasar.dao.unit.S2DaoTestCase;
import org.seasar.extension.jdbc.ResultSetHandler;

/**
 * @author jundu
 * 
 */
public class FetchDtoMetaDataResultSetHandlerTest extends S2DaoTestCase {

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        include("j2ee.dicon");
    }

    public void testHandle() throws Exception {
        String sql = "select empno, ename, dname from emp, dept where empno = 7788 and emp.deptno = dept.deptno";
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        final List<EmployeeDto> ret = new ArrayList<EmployeeDto>();
        ResultSetHandler handler = new FetchDtoMetaDataResultSetHandler(
                createDtoMetaData(EmployeeDto.class), createRowCreator(),
                new FetchHandler<EmployeeDto>() {
                    public boolean execute(EmployeeDto bean) {
                        ret.add(bean);
                        return true;
                    };
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
        assertNotNull(ret);
        assertEquals(1, ret.size());
        EmployeeDto dto = (EmployeeDto) ret.get(0);
        assertEquals(7788, dto.getEmpno());
        assertEquals("SCOTT", dto.getEname());
        assertEquals("RESEARCH", dto.getDname());
    }

    public void testHandle2() throws Exception {
        String sql = "select employee_id, employee_name from emp4 where employee_id = 7369";
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        final List<EmployeeDto3> ret = new ArrayList<EmployeeDto3>();
        ResultSetHandler handler = new FetchDtoMetaDataResultSetHandler(
                createDtoMetaData(EmployeeDto3.class), createRowCreator(),
                new FetchHandler<EmployeeDto3>() {
                    public boolean execute(EmployeeDto3 bean) {
                        ret.add(bean);
                        return true;
                    };
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
        assertNotNull(ret);
        assertEquals(1, ret.size());
        EmployeeDto3 dto = (EmployeeDto3) ret.get(0);
        assertEquals(7369, dto.getEmployeeId());
        assertEquals("SMITH", dto.getEmployeeName());
    }

    protected RowCreator createRowCreator() {
        return new RowCreatorImpl();
    }

}
