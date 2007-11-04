/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.dao.annotation.tiger.impl;

import java.util.List;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.CheckSingleRowUpdate;
import org.seasar.dao.annotation.tiger.NoPersistentProperty;
import org.seasar.dao.annotation.tiger.PersistentProperty;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = Employee.class)
public interface EmployeeAutoDao {

    @Arguments("deptno")
    @Query("deptno asc, empno desc")
    public List getEmployeeByDeptno(int deptno);

    /**
     * @param minSal
     * @param maxSal
     */
    @Query("sal BETWEEN ? AND ? ORDER BY empno")
    public List getEmployeesBySal(Float minSal, Float maxSal);

    /**
     * 
     * @return
     */
    @Arguments( { "enames", "jobs" })
    @Query("ename IN /*enames*/('SCOTT','MARY') AND job IN /*jobs*/('ANALYST', 'FREE')")
    public List getEmployeesByEnameJob(List enames, List jobs);

    public List getEmployeesBySearchCondition(EmployeeSearchCondition dto);

    @Query("department.dname = /*dto.department.dname*/'RESEARCH'")
    public List getEmployeesBySearchCondition2(EmployeeSearchCondition dto);

    public List getEmployeesByEmployee(Employee dto);

    @Arguments("empno")
    public Employee getEmployee(int empno);

    public void insert(Employee employee);

    @NoPersistentProperty( { "job", "mgr", "hiredate", "sal", "comm", "deptno" })
    public void insert2(Employee employee);

    /**
     * @param employee
     */
    @PersistentProperty("deptno")
    public void insert3(Employee employee);

    public void insertBatch(Employee[] employees);

    public void update(Employee employee);

    /**
     * @param employee
     */
    @NoPersistentProperty( { "job", "mgr", "hiredate", "sal", "comm", "deptno" })
    public void update2(Employee employee);

    /**
     * @param employee
     */
    @PersistentProperty("deptno")
    public void update3(Employee employee);

    @CheckSingleRowUpdate(false)
    public int update4(Employee employee);

    public void updateBatch(Employee[] employees);

    public void delete(Employee employee);

    public void deleteBatch(Employee[] employees);
}
