package examples.dao.tiger;

import java.util.List;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;
/**
 * 
 * @author è„å¥Å@åcéO
 * 
 */
@S2Dao(bean=Employee.class)
public interface EmployeeAutoDao {

	public List getAllEmployees();

	@Arguments({"job","deptno"})
	public List getEmployeeByJobDeptno(String job, Integer deptno);
	
	@Arguments({"empno"})
	public Employee getEmployeeByEmpno(int empno);

	@Query("sal BETWEEN ? AND ? ORDER BY empno")
	public List getEmployeesBySal(float minSal, float maxSal);

	@Arguments({"dname_0"})
	public List getEmployeeByDname(String dname);
	
	public List getEmployeesBySearchCondition(EmployeeSearchCondition dto);
	
	public void update(Employee employee);
}