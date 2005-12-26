package org.seasar.dao.annotation.tiger.impl;

import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;

@S2Dao(bean=Employee5.class)
public interface Employee7Dao {

	/**
	 * @return
	 */
	@Sql("SELECT COUNT(*) FROM emp")
	public int getCount();
	
	/**
	 * @param empno
	 * @return
	 */
	@Sql("DELETE FROM emp WHERE empno=?")
	public int deleteEmployee(int empno);
}
