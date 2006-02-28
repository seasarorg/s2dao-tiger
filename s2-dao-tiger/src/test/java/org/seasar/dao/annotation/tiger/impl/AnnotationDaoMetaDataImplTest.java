package org.seasar.dao.annotation.tiger.impl;

import org.seasar.dao.AnnotationReaderFactory;

/**
 * @author higa
 *  
 */
public class AnnotationDaoMetaDataImplTest extends DaoMetaDataImplTest {
	private AnnotationReaderFactory readerFactory;

	/**
	 * Constructor for InvocationImplTest.
	 * 
	 * @param arg0
	 */
	public AnnotationDaoMetaDataImplTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(AnnotationDaoMetaDataImplTest.class);
	}

	public void setUp() {
		include("AnnotationDaoMetaDataImplTest.dicon");
	}
	protected Object getBean(String className) {
		if(className.equals("Employee")){
			return new Employee();
		}else if(className.equals("Employee3")){
			return new Employee3();
		}else if(className.equals("EmployeeSearchCondition")){
			return new EmployeeSearchCondition();
		}else if(className.equals("Department")){
			return new Department();
		}
		fail();
		return null;
	}
	protected Class getBeanClass(String className) {
		if(className.equals("Employee")){
			return Employee.class;
		}
		fail();
		return null;
	}
	protected Class getDaoClass(String className) {
		if(className.equals("EmployeeDao")){
			return EmployeeDao.class;
		}else if(className.equals("EmployeeAutoDao")){
			return EmployeeAutoDao.class;
		}else if(className.equals("IllegalEmployeeAutoDao")){
			return IllegalEmployeeAutoDao.class;
		}else if(className.equals("Employee2Dao")){
			return Employee2Dao.class;
		}else if(className.equals("Employee3Dao")){
			return Employee3Dao.class;
		}else if(className.equals("Employee4Dao")){
			return Employee4Dao.class;
		}else if(className.equals("Employee5Dao")){
			return Employee5Dao.class;
		}else if(className.equals("Employee6Dao")){
			return Employee6Dao.class;
		}else if(className.equals("Employee7Dao")){
			return Employee7Dao.class;
		}else if(className.equals("DepartmentTotalSalaryDao")){
			return DepartmentTotalSalaryDao.class;
		}
		fail();
		return null;
	}

}