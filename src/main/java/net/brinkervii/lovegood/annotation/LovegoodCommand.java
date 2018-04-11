package net.brinkervii.lovegood.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LovegoodCommand {
	String name();
	String description() default "";
}
