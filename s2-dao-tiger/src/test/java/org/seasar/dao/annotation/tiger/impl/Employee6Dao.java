package org.seasar.dao.annotation.tiger.impl;

import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean=Employee5.class)
public interface Employee6Dao {

	/**
	 * @param dto
	 * @return
	 */
	@Query("/*IF $dto.orderByString != null*/order by /*$dto.orderByString*/ENAME /*END*/")
	public Employee[] getEmployees(EmployeeSearchCondition dto);
}
