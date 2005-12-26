package examples.dao.tiger;

import java.util.List;

import org.seasar.dao.annotation.tiger.S2Dao;


public interface Employee2Dao {
	
	public List getEmployees(String ename);


	public Employee getEmployee(int empno);

}
