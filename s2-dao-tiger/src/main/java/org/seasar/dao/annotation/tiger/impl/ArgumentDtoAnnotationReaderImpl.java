/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.seasar.dao.ArgumentDtoAnnotationReader;
import org.seasar.dao.annotation.tiger.ProcedureParameter;
import org.seasar.dao.annotation.tiger.ValueType;
import org.seasar.dao.impl.FieldArgumentDtoAnnotationReader;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;

/**
 * Tigerのアノテーションを読み取る{@link ArgumentDtoAnnotationReader}の実装クラスです。
 * 
 * @author taedium
 */
public class ArgumentDtoAnnotationReaderImpl extends
        FieldArgumentDtoAnnotationReader {

    @Override
    public String getProcedureParameter(final BeanDesc dtoDesc,
            final Field field) {
        final ProcedureParameter parameter = getAnnotation(dtoDesc, field,
                ProcedureParameter.class);
        if (parameter != null) {
            return parameter.value().name().toLowerCase();
        }
        return super.getProcedureParameter(dtoDesc, field);
    }

    @Override
    public String getValueType(final BeanDesc dtoDesc, final Field field) {
        final ValueType valueType = getAnnotation(dtoDesc, field,
                ValueType.class);
        if (valueType != null) {
            return valueType.value();
        }
        return super.getValueType(dtoDesc, field);
    }

    protected <T extends Annotation> T getAnnotation(final BeanDesc beanDesc,
            final Field field, final Class<T> annotationClass) {
        if (field.isAnnotationPresent(annotationClass)) {
            return field.getAnnotation(annotationClass);
        }
        if (beanDesc.hasPropertyDesc(field.getName())) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(field.getName());
            if (pd.hasWriteMethod()) {
                Method method = pd.getWriteMethod();
                if (method.isAnnotationPresent(annotationClass)) {
                    return method.getAnnotation(annotationClass);
                }
            }
            if (pd.hasReadMethod()) {
                Method method = pd.getReadMethod();
                return method.getAnnotation(annotationClass);
            }
        }
        return null;
    }

}
