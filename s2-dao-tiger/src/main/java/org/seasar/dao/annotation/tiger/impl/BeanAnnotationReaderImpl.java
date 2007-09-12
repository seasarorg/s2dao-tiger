/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.lang.annotation.Annotation;

import org.seasar.dao.Dbms;
import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Column;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;
import org.seasar.dao.annotation.tiger.Ids;
import org.seasar.dao.annotation.tiger.Relation;
import org.seasar.dao.annotation.tiger.ValueType;
import org.seasar.dao.impl.FieldBeanAnnotationReader;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author keizou
 * @author manhole
 * @author azusa
 * 
 */
public class BeanAnnotationReaderImpl extends FieldBeanAnnotationReader {

    private Class<?> beanClass_;

    private Bean bean_;

    @SuppressWarnings("unchecked")
    public BeanAnnotationReaderImpl(Class beanClass) {
        super(beanClass);
        this.beanClass_ = beanClass;
        bean_ = (Bean) beanClass_.getAnnotation(Bean.class);
    }

    private <T extends Annotation> T getPropertyAnnotation(Class<T> clazz,
            PropertyDesc pd) {
        BeanDesc bd = BeanDescFactory.getBeanDesc(beanClass_);
        if (bd.hasField(pd.getPropertyName())) {
            T fieldAnnotation = bd.getField(pd.getPropertyName())
                    .getAnnotation(clazz);
            if (fieldAnnotation != null) {
                return fieldAnnotation;
            }
        }

        if (pd.getWriteMethod() != null) {
            T annotation = pd.getWriteMethod().getAnnotation(clazz);
            if (annotation != null) {
                return annotation;
            }
        }
        if (pd.getReadMethod() != null) {
            return pd.getReadMethod().getAnnotation(clazz);
        }
        return null;
    }

    public String getColumnAnnotation(PropertyDesc pd) {
        Column column = getPropertyAnnotation(Column.class, pd);
        return (column != null) ? column.value() : super
                .getColumnAnnotation(pd);
    }

    public String getTableAnnotation() {

        if (bean_ == null || bean_.table().length() == 0) {
            return super.getTableAnnotation();
        }
        return bean_.table();
    }

    public String getVersionNoPropertyName() {
        return (bean_ != null) ? bean_.versionNoProperty() : super
                .getVersionNoPropertyName();
    }

    public String getTimestampPropertyName() {
        return (bean_ != null) ? bean_.timeStampProperty() : super
                .getTimestampPropertyName();
    }

    public String getId(PropertyDesc pd, Dbms dbms) {
        String dbmsSuffix = dbms.getSuffix();
        Id id = getIds(pd, dbmsSuffix);
        if (id == null) {
            id = getPropertyAnnotation(Id.class, pd);
            if (id == null) {
                return super.getId(pd, dbms);
            }
            if (("_" + id.dbms()).equals(dbms.getSuffix())
                    || id.dbms().equals("")) {
                return getIdName(id);
            } else {
                return null;
            }
        } else {
            return getIdName(id);
        }
    }

    public String[] getNoPersisteneProps() {
        return (bean_ != null) ? bean_.noPersistentProperty() : super
                .getNoPersisteneProps();
    }

    public boolean hasRelationNo(PropertyDesc pd) {
        Relation rel = getPropertyAnnotation(Relation.class, pd);
        return (rel != null) ? rel != null : super.hasRelationNo(pd);
    }

    public int getRelationNo(PropertyDesc pd) {
        Relation rel = getPropertyAnnotation(Relation.class, pd);
        return (rel != null) ? rel.relationNo() : super.getRelationNo(pd);
    }

    public String getRelationKey(PropertyDesc pd) {
        Relation rel = getPropertyAnnotation(Relation.class, pd);
        return (rel != null) ? rel.relationKey() : super.getRelationKey(pd);
    }

    public String getValueType(PropertyDesc pd) {
        ValueType valueType = (ValueType) getPropertyAnnotation(
                ValueType.class, pd);
        return (valueType != null) ? valueType.value() : super.getValueType(pd);
    }

    protected Id getIds(PropertyDesc pd, String dbmsSuffix) {
        Ids ids = getPropertyAnnotation(Ids.class, pd);
        if (ids == null || ids.value().length == 0) {
            return null;
        }
        Id defaultId = null;
        for (int i = 0; i < ids.value().length; i++) {
            Id id = ids.value()[i];
            if (dbmsSuffix.equals("_" + id.dbms())) {
                return id;
            }
            if ("".equals(id.dbms())) {
                defaultId = id;
            }
        }
        return defaultId;
    }

    protected String getIdName(Id id) {
        if (id.value().equals(IdType.SEQUENCE) && id.sequenceName() != null) {
            return id.value().name().toLowerCase() + ", sequenceName="
                    + id.sequenceName();
        }
        return id.value().name().toLowerCase();
    }

}
