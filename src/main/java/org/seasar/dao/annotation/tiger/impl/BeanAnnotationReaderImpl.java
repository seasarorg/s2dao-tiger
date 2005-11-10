package org.seasar.dao.annotation.tiger.impl;

import java.lang.annotation.Annotation;

import org.seasar.dao.BeanAnnotationReader;
import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Column;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;
import org.seasar.dao.annotation.tiger.Relation;
import org.seasar.framework.beans.PropertyDesc;

public class BeanAnnotationReaderImpl implements BeanAnnotationReader {
	private Class beanClass_;
	private Bean bean_;
	public BeanAnnotationReaderImpl(Class beanClass) {
		this.beanClass_ = beanClass;
		bean_ = (Bean) beanClass_.getAnnotation(Bean.class);
	}
	private <T extends Annotation> T getPropertyAnnotation(Class<T> clazz,PropertyDesc pd){
		T annotation = pd.getWriteMethod().getAnnotation(clazz);
		if(annotation!=null)
			return annotation;
		return pd.getReadMethod().getAnnotation(clazz);
	}
	public String getColumnAnnotation(PropertyDesc pd) {
		Column column = getPropertyAnnotation(Column.class,pd);
		return (column!=null)?column.value():null;
	}

	public String getTableAnnotation() {
		return (bean_!=null)?bean_.table():null;
	}

	public String getVersionNoProteryNameAnnotation() {
		return (bean_!=null)?bean_.versionNoProperty():null;
	}

	public String getTimestampPropertyName() {
		return (bean_!=null)?bean_.timeStampProperty():null;
	}

	public String getId(PropertyDesc pd) {
		Id id = getPropertyAnnotation(Id.class,pd);
		if(id==null){
			return null;
		}
		if(id.value().equals(IdType.SEQUENCE) && id.sequenceName()!=null){
			return id.value().name().toLowerCase()+
			", sequenceName=" + id.sequenceName();
		}
		return id.value().name().toLowerCase();
	}

	public String[] getNoPersisteneProps() {
		return (bean_!=null)?bean_.noPersistentProperty():null;
	}

	public boolean hasRelationNo(PropertyDesc pd) {
		Relation rel = getPropertyAnnotation(Relation.class,pd);
		return (rel!=null);
	}

	public int getRelationNo(PropertyDesc pd) {
		Relation rel = getPropertyAnnotation(Relation.class,pd);
		return (rel!=null)?rel.relationNo():0;
	}

	public String getRelationKey(PropertyDesc pd) {
		Relation rel = getPropertyAnnotation(Relation.class,pd);
		return (rel!=null)?rel.relationKey():null;
	}

}
