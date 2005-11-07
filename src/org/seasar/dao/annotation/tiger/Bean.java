package org.seasar.dao.annotation.tiger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Bean {
	String table() default "";
	String[] noPersistentProperty() default {};
	String versionNoProperty() default "versionNo";
	String timeStampProperty() default "timestamp";
}
