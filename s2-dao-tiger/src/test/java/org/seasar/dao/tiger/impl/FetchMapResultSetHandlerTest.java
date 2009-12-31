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
package org.seasar.dao.tiger.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.seasar.dao.tiger.FetchHandler;
import org.seasar.dao.unit.S2DaoTestCase;
import org.seasar.extension.jdbc.ResultSetHandler;

/**
 * @author jundu
 * 
 */
public class FetchMapResultSetHandlerTest extends S2DaoTestCase {

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        include("j2ee.dicon");
    }

    /**
     * Test method for
     * {@link org.seasar.dao.tiger.impl.FetchMapResultSetHandler#handle(java.sql.ResultSet)}.
     */
    public void testHandle() throws Exception {
        String sql = "select employee_id, employee_name from emp4 where employee_id = 7369";
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        final List<Map> list = new ArrayList<Map>();
        ResultSetHandler handler = new FetchMapResultSetHandler(
                new FetchHandler<Map>() {
                    public boolean execute(Map row) {
                        list.add(row);
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
        assertNotNull(list);
        assertEquals(1, list.size());
        Map row = list.get(0);
        assertEquals(Integer.valueOf(7369), row.get("employeeId"));
        assertEquals("SMITH", row.get("employeeName"));
    }
}
