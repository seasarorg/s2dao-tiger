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
package org.seasar.dao.annotation.tiger.util;

import java.lang.annotation.Annotation;

import org.seasar.dao.util.ImplementInterfaceWalker;
import org.seasar.dao.util.ImplementInterfaceWalker.Status;

/**
 * @author jundu
 * 
 */
public class AnnotationUtil {

    private AnnotationUtil() {
    }

    public static <T extends Annotation> T getAnnotation(Class<?> clazz,
            Class<T> annotationClass) {
        return getAnnotation0(clazz, annotationClass);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Annotation> T getAnnotation0(Class<?> clazz,
            Class<T> annotationClass) {
        final T annotation = getAnnotationFromClass(clazz, annotationClass);
        if (annotation != null) {
            return annotation;
        }

        HandlerImpl handlerImpl = new HandlerImpl(annotationClass);
        ImplementInterfaceWalker.walk(clazz, handlerImpl);
        return (T) handlerImpl.foundClass;
    }

    private static class HandlerImpl<T extends Annotation> implements
            ImplementInterfaceWalker.Handler {

        T foundClass;

        private Class<T> annotationClass;

        public HandlerImpl(Class<T> annotationClass) {
            this.annotationClass = annotationClass;
        }

        public Status accept(Class ifs) {
            final T beanClass = getAnnotationFromClass(ifs, annotationClass);
            if (beanClass != null) {
                foundClass = beanClass;
                return ImplementInterfaceWalker.BREAK;
            }
            return ImplementInterfaceWalker.CONTINUE;
        }
    }

    private static <T extends Annotation> T getAnnotationFromClass(
            Class<?> clazz, Class<T> annotationClass) {
        if (clazz.isAnnotationPresent(annotationClass)) {
            return (T) clazz.getAnnotation(annotationClass);
        }
        return null;
    }
}
