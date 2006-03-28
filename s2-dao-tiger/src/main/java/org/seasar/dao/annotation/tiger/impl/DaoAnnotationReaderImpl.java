/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import java.lang.reflect.Method;

import org.seasar.dao.DaoAnnotationReader;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.NoPersistentProperty;
import org.seasar.dao.annotation.tiger.PersistentProperty;
import org.seasar.dao.annotation.tiger.Procedure;
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
	public String getStoredProcedureName(Method method) {
		Procedure procedure = method.getAnnotation(Procedure.class);
		return (procedure!=null)?procedure.value():null;
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
