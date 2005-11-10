package org.seasar.dao.annotation.tiger;

public @interface Id {
	IdType value() default IdType.ASSIGNED;
	String sequenceName() default "";
}
