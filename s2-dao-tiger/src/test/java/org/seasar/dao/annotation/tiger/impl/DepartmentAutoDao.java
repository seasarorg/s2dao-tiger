package org.seasar.dao.annotation.tiger.impl;

import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean=Department.class)
public interface DepartmentAutoDao {
	
	public void insert(Department department);
	
	public void insertBatch(Department[] departents);
	
	public void update(Department department);
	
	public void updateBatch(Department[] departents);
	
	public void delete(Department department);
	
	public void deleteBatch(Department[] departents);
}
