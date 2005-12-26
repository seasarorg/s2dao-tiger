package org.seasar.dao.annotation.tiger.impl;

import java.util.List;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean=Employee.class)
public interface EmployeeDao {
	
	public List getAllEmployees();
	
	public Employee[] getAllEmployeeArray();
	
	/**
	 * @param empno
	 * @return
	 */
	@Arguments("empno")
	public Employee getEmployee(int empno);
	
	public int getCount();
	
	public void update(Employee employee);
	
	public Employee[] getEmployeesByDeptno(int deptno);
}
