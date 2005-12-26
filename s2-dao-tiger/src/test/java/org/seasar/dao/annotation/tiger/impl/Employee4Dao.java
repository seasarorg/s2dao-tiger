package org.seasar.dao.annotation.tiger.impl;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean=Employee4.class)
public interface Employee4Dao {
	
	@Arguments("empno")
	public Employee4 getEmployee(int empno);
}
