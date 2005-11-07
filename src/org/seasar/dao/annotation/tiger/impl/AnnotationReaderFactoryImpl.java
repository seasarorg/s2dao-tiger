package org.seasar.dao.annotation.tiger.impl;

import org.seasar.dao.AnnotationReaderFactory;
import org.seasar.dao.BeanAnnotationReader;
import org.seasar.dao.DaoAnnotationReader;
import org.seasar.framework.beans.BeanDesc;

public class AnnotationReaderFactoryImpl implements
		AnnotationReaderFactory {

	public DaoAnnotationReader createDaoAnnotationReader(BeanDesc daoBeanDesc) {
		return new DaoAnnotationReaderImpl(daoBeanDesc);
	}

	public BeanAnnotationReader createBeanAnnotationReader(Class beanClass_) {
		return new BeanAnnotationReaderImpl(beanClass_);
	}

}
