package org.seasar.dao.annotation.tiger;

import java.lang.annotation.Inherited;

@Inherited
public @interface Id {
	IdType value() default IdType.ASSIGNED;
	String sequenceName() default "";
}
