package com.github.skiedrowski.tools.cdiproperties

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import jakarta.enterprise.inject.spi.Annotated
import jakarta.enterprise.inject.spi.InjectionPoint

class PropertiesReaderTest {
    private val propertiesReader = PropertiesReader(CachingPropertyFileReader())

    @BeforeEach
    fun context() {
        System.setProperty(CachingPropertyFileReader.CONFIG_DIR_PROPERTY, "src/test/resources/")
    }

    @Test
    fun provideProperties() {
        val ip = buildInjectionPoint("my.properties")

        val properties = propertiesReader.provideProperties(ip)

        properties["key2"].toString() shouldBe "value2"
    }

    @Test
    fun `provideProperties is caching`() {
        val ip = buildInjectionPoint("my.properties")

        val properties1 = propertiesReader.provideProperties(ip)
        val properties2 = propertiesReader.provideProperties(ip)

        properties1 shouldBeSameInstanceAs properties2
    }

    @Test
    fun `provideProperties file nonexistant returns empty properties`() {
        val ip = buildInjectionPoint("öksldfhtest.properties")

        val properties = propertiesReader.provideProperties(ip)

        properties.size shouldBe 0
    }

    private fun buildInjectionPoint(filename: String): InjectionPoint {
        val propertiesFromFile = mockk<PropertiesFromFile> {
            every { value } returns filename
        }

        val annotated = mockk<Annotated> {
            every { getAnnotation(PropertiesFromFile::class.java) } returns propertiesFromFile
        }

        val injectionPoint = mockk<InjectionPoint>()
        every { injectionPoint.annotated } returns annotated
        return injectionPoint
    }

}