package examples.dao;

import java.util.List;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;
/**
 * 
 * @author è„å¥Å@åcéO
 */
@S2Dao(bean=Employee.class)
public interface EmployeeDao {
	
	public List getAllEmployees();
	
	/**
	 * @param empno
	 * @return
	 */
	@Arguments("empno")
	public Employee getEmployee(int empno);
	
	/**
	 * @param empno
	 * @return
	 */
	@Sql("SELECT count(*) FROM emp")
	public int getCount();
		
	/**
	 * @param empno
	 * @return
	 */
	@Arguments({"job","deptno"})
	public List getEmployeeByJobDeptno(String job, Integer deptno);
	
	public int update(Employee employee);
}
