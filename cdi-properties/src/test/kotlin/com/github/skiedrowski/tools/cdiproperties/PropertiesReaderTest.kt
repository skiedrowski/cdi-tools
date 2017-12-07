package com.github.skiedrowski.tools.cdiproperties

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.sameInstance
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import javax.enterprise.inject.spi.Annotated
import javax.enterprise.inject.spi.InjectionPoint

class PropertiesReaderTest {
    @JvmField
    @Rule
    val thrown: ExpectedException = ExpectedException.none()

    private val propertiesReader = PropertiesReader(CachingPropertyFileReader())

    @Before
    fun context() {
        System.setProperty(CachingPropertyFileReader.CONFIG_DIR_PROPERTY, "src/test/resources/")
    }
    
    @Test
    fun provideProperties() {
        val ip = buildInjectionPoint("my.properties")

        val properties = propertiesReader.provideProperties(ip)

        assertThat(properties["key2"].toString(), equalTo("value2"))
    }

    @Test
    fun `provideProperties is caching`() {
        val ip = buildInjectionPoint("my.properties")

        val properties1 = propertiesReader.provideProperties(ip)
        val properties2 = propertiesReader.provideProperties(ip)
        
        assertThat(properties1, sameInstance(properties2))
    }

    @Test
    fun `provideProperties file nonexistant returns empty properties`() {
        val ip = buildInjectionPoint("öksldfhtest.properties")

        val properties = propertiesReader.provideProperties(ip)
        
        assertThat(properties.size, equalTo(0))
    }

    private fun buildInjectionPoint(filename: String): InjectionPoint {
        val propertiesFromFile = mock<PropertiesFromFile> {
            on { value } doReturn filename
        }

        val annotated = mock<Annotated> {
            on { getAnnotation(PropertiesFromFile::class.java) } doReturn propertiesFromFile
        }

        return mock {
            on { this.annotated } doReturn annotated
        }
    }

}