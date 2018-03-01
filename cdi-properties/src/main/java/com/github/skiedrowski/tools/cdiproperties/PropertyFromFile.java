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
public @interface PropertyFromFile {
	/** filename relative to the directory specified in CachingPropertyFileReader.CONFIG_DIR_PROPERTY */
	@Nonbinding String filename() default "config.properties";
	/** key in property file; if left empty, the name of the member with this annotation is used as the key */
	@Nonbinding String key() default "";
}


/*
              Does not work with Kotlin!
              The @Nonbinding seems to be ignored by kotlin compiler (see https://youtrack.jetbrains.com/issue/KT-14338)


 @Qualifier
 @Target(AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
 @Retention(AnnotationRetention.RUNTIME)
 annotation class PropertyFromFile(
 @get:Nonbinding val filename: String = "config.properties",
 @get:Nonbinding val key: String = "")
 */