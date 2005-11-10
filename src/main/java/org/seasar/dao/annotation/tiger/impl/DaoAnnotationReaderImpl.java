package org.seasar.dao.annotation.tiger.impl;

import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

import org.seasar.dao.DaoAnnotationReader;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.NoPersistentProperty;
import org.seasar.dao.annotation.tiger.PersistentProperty;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;
import org.seasar.framework.beans.BeanDesc;

public class DaoAnnotationReaderImpl implements DaoAnnotationReader {
	private BeanDesc daoBeanDesc_;
	private Class daoClass_;
	public DaoAnnotationReaderImpl(BeanDesc daoBeanDesc) {
		daoBeanDesc_ = daoBeanDesc;
		daoClass_ = daoBeanDesc.getBeanClass();
	}
	public String getQuery(Method method) {
		Query query = method.getAnnotation(Query.class);
		return (query!=null)?query.value():null;
	}

	public String[] getArgNames(Method method) {
		Arguments arg = method.getAnnotation(Arguments.class);
		return (arg!=null)?arg.value():new String[0];
	}
	public Class getBeanClass() {
		if(daoClass_.isAnnotationPresent(S2Dao.class)){
			S2Dao s2dao = (S2Dao) daoClass_.getAnnotation(S2Dao.class);
			return s2dao.bean();
		}
		return null;
	}

	public String[] getNoPersistentProps(Method method) {
		NoPersistentProperty npp = method.getAnnotation(NoPersistentProperty.class);
		return (npp!=null)?npp.value():null;
	}

	public String[] getPersistentProps(Method method) {
		PersistentProperty pp = (PersistentProperty) method.getAnnotation(PersistentProperty.class);
		return (pp!=null)?pp.value():null;
	}

	public String getSQL(Method method, String suffix) {
		Sql sql = method.getAnnotation(Sql.class);
		return (sql!=null)?sql.value():null;
	}

}
