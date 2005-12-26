package org.seasar.dao.annotation.tiger.impl;

import java.util.List;

import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean=Employee3.class)
public interface Employee3Dao {
	
	public List getEmployees(Employee3 dto);
	
	/**
	 * @param dto
	 * @return
	 */
	@Query("ORDER BY empno")
	public List getEmployees2(Employee3 dto);
}
