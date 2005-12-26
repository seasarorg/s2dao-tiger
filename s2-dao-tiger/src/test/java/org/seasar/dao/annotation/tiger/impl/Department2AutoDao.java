package org.seasar.dao.annotation.tiger.impl;

import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean=Department2.class)
public interface Department2AutoDao {
	
	public Department2 getDepartment(int deptno);
	
	public void insert(Department2 department);
}
