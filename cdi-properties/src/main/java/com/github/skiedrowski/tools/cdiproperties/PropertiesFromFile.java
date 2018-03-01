package com.github.skiedrowski.tools.cdiproperties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Qualifier
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertiesFromFile {
	/** filename relative to the directory specified in CachingPropertyFileReader.CONFIG_DIR_PROPERTY */
	@Nonbinding String value() default "config.properties";
}

/*
              Does not work with Kotlin!
              The @Nonbinding seems to be ignored by kotlin compiler (see https://youtrack.jetbrains.com/issue/KT-14338)


@Qualifier
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PropertiesFromFile(
        @JvmField @get:Nonbinding val value: String = "config.properties")
 */
