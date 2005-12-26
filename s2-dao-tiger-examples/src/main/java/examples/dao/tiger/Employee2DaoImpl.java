package examples.dao.tiger;

import java.util.List;

import org.seasar.dao.DaoMetaDataFactory;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.impl.AbstractDao;

@S2Dao(bean=Employee.class)
public abstract class Employee2DaoImpl extends AbstractDao implements Employee2Dao {
	public Employee2DaoImpl(DaoMetaDataFactory daoMetaDataFactory) {
		super(daoMetaDataFactory);
	}
	
	@Arguments("empno")
	public List getEmployees(String ename) {
		return getEntityManager().find("ename LIKE ?", "%" + ename + "%");
	}
}