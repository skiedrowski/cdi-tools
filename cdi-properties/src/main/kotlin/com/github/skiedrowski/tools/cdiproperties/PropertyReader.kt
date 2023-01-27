package com.github.skiedrowski.tools.cdiproperties

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import jakarta.enterprise.inject.spi.InjectionPoint
import jakarta.inject.Inject

@ApplicationScoped
class PropertyReader @Inject constructor(private val cachingPropertyFileReader: CachingPropertyFileReader) {

    @Produces
    @PropertyFromFile
    fun provideStringProperty(ip: InjectionPoint): String? {
        val propertyFromFileAnn = ip.annotated.getAnnotation(PropertyFromFile::class.java)
        val filename = propertyFromFileAnn.filename
        val key = if (propertyFromFileAnn.key.isEmpty()) ip.member.name else propertyFromFileAnn.key
        return readPropertyFromFile(filename, key)
    }

    @Produces
    @PropertyFromFile
    fun provideIntProperty(ip: InjectionPoint): Int? {
        val propertyFromFileAnn = ip.annotated.getAnnotation(PropertyFromFile::class.java)
        val filename = propertyFromFileAnn.filename
        val key = if (propertyFromFileAnn.key.isEmpty()) ip.member.name else propertyFromFileAnn.key
        return readPropertyFromFile(filename, key)?.toInt()
    }

    @Produces
    @PropertyFromFile
    fun provideBoolProperty(ip: InjectionPoint): Boolean? {
        val propertyFromFileAnn = ip.annotated.getAnnotation(PropertyFromFile::class.java)
        val filename = propertyFromFileAnn.filename
        val key = if (propertyFromFileAnn.key.isEmpty()) ip.member.name else propertyFromFileAnn.key
        return readPropertyFromFile(filename, key)?.toBoolean()
    }

    private fun readPropertyFromFile(filename: String, key: String): String? {
        val properties = cachingPropertyFileReader.getProperties(filename)
        return properties[key] as String?
    }
}