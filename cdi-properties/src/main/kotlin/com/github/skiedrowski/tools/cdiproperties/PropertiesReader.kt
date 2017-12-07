package com.github.skiedrowski.tools.cdiproperties

import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.enterprise.inject.spi.InjectionPoint
import javax.inject.Inject

@ApplicationScoped
class PropertiesReader @Inject constructor(private val cachingPropertyFileReader: CachingPropertyFileReader) {

    @Produces
    @PropertiesFromFile
    fun provideProperties(ip: InjectionPoint): Properties {
        val filename = ip.annotated.getAnnotation(PropertiesFromFile::class.java).value
        return cachingPropertyFileReader.getProperties(filename)
    }
}