package com.texelz.atgrestful;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The powefull annotation that will resolve a nucleus component for you
 * 
 * @author Onhate
 * 
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Nucleus {

	String value();
}