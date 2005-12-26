package org.seasar.dao.annotation.tiger.impl;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean=Employee5.class)
public interface Employee5Dao {

	@Arguments("empno")
	public Employee5 getEmployee(int empno);
}
