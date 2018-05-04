package net.brinkervii.lovegood.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LovegoodServiceParams {
	boolean debug() default false;
}
