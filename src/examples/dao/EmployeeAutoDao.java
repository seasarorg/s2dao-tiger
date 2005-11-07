package examples.dao;

import java.util.List;
/**
 * 
 * @author è„å¥Å@åcéO
 * @S2Dao(bean=examples.dao.backport175.Employee.class)
 */
public interface EmployeeAutoDao {

	public List getAllEmployees();

	/**
	 * @Arguments (
	 * 	{"job","deptno"}
	 * )
	 */
	public List getEmployeeByJobDeptno(String job, Integer deptno);
	
	/**
	 * @Arguments (
	 * 	{"empno"}
	 * )
	 */
	public Employee getEmployeeByEmpno(int empno);

	/**
	 * @Query (
	 * 	"sal BETWEEN ? AND ? ORDER BY empno"
	 * )
	 */
	public List getEmployeesBySal(float minSal, float maxSal);

	/**
	 * @Arguments (
	 * 	{"dname_0"}
	 * )
	 */
	public List getEmployeeByDname(String dname);
	
	public List getEmployeesBySearchCondition(EmployeeSearchCondition dto);
	
	public void update(Employee employee);
}