package org.seasar.dao.annotation.tiger.impl;

import java.util.List;

import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean=DepartmentTotalSalary.class)
public interface DepartmentTotalSalaryDao {

	public List getTotalSalaries();
}
