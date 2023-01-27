package com.github.skiedrowski.tools.cdiproperties

import java.util.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import jakarta.enterprise.inject.spi.InjectionPoint
import jakarta.inject.Inject

@ApplicationScoped
class PropertiesReader @Inject constructor(private val cachingPropertyFileReader: CachingPropertyFileReader) {

    @Produces
    @PropertiesFromFile
    fun provideProperties(ip: InjectionPoint): Properties {
        val filename = ip.annotated.getAnnotation(PropertiesFromFile::class.java).value
        return cachingPropertyFileReader.getProperties(filename)
    }
}