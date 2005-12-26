package org.seasar.dao.annotation.tiger.impl;

import java.util.List;

import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean=Employee2.class)
public interface Employee2Dao {
	public List getAllEmployees();
}
