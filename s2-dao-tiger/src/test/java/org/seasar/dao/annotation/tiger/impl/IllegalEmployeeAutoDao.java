package org.seasar.dao.annotation.tiger.impl;

import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean=Employee5.class)
public interface IllegalEmployeeAutoDao {
	
	public void insertIllegal(int empno);
}
