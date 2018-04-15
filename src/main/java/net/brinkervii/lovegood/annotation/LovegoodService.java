package net.brinkervii.lovegood.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LovegoodService {
	boolean debug() default false;
}
