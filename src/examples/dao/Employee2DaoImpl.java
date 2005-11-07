package examples.dao;

import java.util.List;

import org.seasar.dao.DaoMetaDataFactory;
import org.seasar.dao.impl.AbstractDao;
/**
 * @S2Dao (
 * 	bean=examples.dao.backport175.Employee.class
 * )
 */
public abstract class Employee2DaoImpl extends AbstractDao implements Employee2Dao {
	public Employee2DaoImpl(DaoMetaDataFactory daoMetaDataFactory) {
		super(daoMetaDataFactory);
	}
	
	/**
	 * @Argument (
	 * 	"empno"
	 * )
	 */
	public List getEmployees(String ename) {
		return getEntityManager().find("ename LIKE ?", "%" + ename + "%");
	}
}