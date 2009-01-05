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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.seasar.dao.impl.AbstractMapResultSetHandler;
import org.seasar.dao.tiger.FetchHandler;
import org.seasar.extension.jdbc.PropertyType;

/**
 * @author jundu
 * 
 */
public class FetchMapResultSetHandler extends AbstractMapResultSetHandler {

    @SuppressWarnings("unchecked")
    protected FetchHandler fetchHandler;

    @SuppressWarnings("unchecked")
    public FetchMapResultSetHandler(FetchHandler fetchHandler) {
        super();
        this.fetchHandler = fetchHandler;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seasar.extension.jdbc.ResultSetHandler#handle(java.sql.ResultSet)
     */
    @SuppressWarnings("unchecked")
    public Object handle(ResultSet rs) throws SQLException {
        PropertyType[] propertyTypes = createPropertyTypes(rs.getMetaData());
        int count = 0;
        while (rs.next()) {
            final Map<?, ?> row = createRow(rs, propertyTypes);
            count++;
            if (!fetchHandler.execute(row)) {
                break;
            }
        }
        return Integer.valueOf(count);
    }

}
