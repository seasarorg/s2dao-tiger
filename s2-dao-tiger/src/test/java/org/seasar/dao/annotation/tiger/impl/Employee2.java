package org.seasar.dao.annotation.tiger.impl;

import java.io.Serializable;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Relation;

@Bean(table="EMP2")
public class Employee2 implements Serializable {

    private long empno;
    private String ename;
    private Short deptnum;

    private Department2 department2;
        

    public Employee2() {
    }
    
    public String toString() {
    	StringBuffer buf = new StringBuffer();
    	buf.append(empno).append(", ");
		buf.append(ename).append(", ");
		buf.append(deptnum).append(", {");
		buf.append(department2).append("}");
    	return buf.toString();
    }	
	/**
	 * @return Returns the department2.
	 */
	public Department2 getDepartment2() {
		return department2;
	}

	@Relation(relationNo=0,relationKey="DEPTNUM:DEPTNO")
	public void setDepartment2(Department2 department2) {
		this.department2 = department2;
	}
	/**
	 * @return Returns the deptnum.
	 */
	public Short getDeptnum() {
		return deptnum;
	}
	/**
	 * @param deptnum The deptnum to set.
	 */
	public void setDeptnum(Short deptnum) {
		this.deptnum = deptnum;
	}
	/**
	 * @return Returns the empno.
	 */
	public long getEmpno() {
		return empno;
	}
	/**
	 * @param empno The empno to set.
	 */
	public void setEmpno(long empno) {
		this.empno = empno;
	}
	/**
	 * @return Returns the ename.
	 */
	public String getEname() {
		return ename;
	}
	/**
	 * @param ename The ename to set.
	 */
	public void setEname(String ename) {
		this.ename = ename;
	}
}
