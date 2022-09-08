package com.github.skiedrowski.tools.cdiproperties

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.reflect.Member
import javax.enterprise.inject.spi.Annotated
import javax.enterprise.inject.spi.InjectionPoint

class PropertyReaderTest {
    private val propertyReader = PropertyReader(CachingPropertyFileReader())

    @BeforeEach
    fun context() {
        System.setProperty(CachingPropertyFileReader.CONFIG_DIR_PROPERTY, "src/test/resources/")
    }

    @Test
    fun provideStringProperty() {
        val ip = buildInjectionPoint("my.properties", "key1")

        val property = propertyReader.provideStringProperty(ip)

        property shouldBe "value1"
    }

    @Test
    fun `provideStringProperty int as string`() {
        val ip = buildInjectionPoint("my.properties", "int1")

        val property = propertyReader.provideStringProperty(ip)

        property shouldBe "12345"
    }

    @Test
    fun `provideStringProperty nonexistant key`() {
        val ip = buildInjectionPoint("my.properties", "oaiwuehf")

        val property = propertyReader.provideStringProperty(ip)

        property shouldBe null
    }

    @Test
    fun provideIntProperty() {
        val ip = buildInjectionPoint("my.properties", "int1")

        val property = propertyReader.provideIntProperty(ip)

        property shouldBe 12345
    }

    @Test
    fun `provideIntProperty nonexistant key`() {
        val ip = buildInjectionPoint("my.properties", "numericdfsdf")

        val property = propertyReader.provideIntProperty(ip)

        property shouldBe null
    }

    @Test
    fun `provideBoolProperty true`() {
        val ip = buildInjectionPoint("my.properties", "bool1")

        val property = propertyReader.provideBoolProperty(ip)

        property shouldBe true
    }

    @Test
    fun `provideBoolProperty false`() {
        val ip = buildInjectionPoint("my.properties", "bool2")

        val property = propertyReader.provideBoolProperty(ip)

        property shouldBe false
    }

    @Test
    fun `provideBoolProperty nonexistant key`() {
        val ip = buildInjectionPoint("my.properties", "numericdfsdf")

        val property = propertyReader.provideBoolProperty(ip)

        property shouldBe null
    }

    @Test
    fun `provideProperty no key specified uses member name`() {
        val ip = buildInjectionPoint("my.properties", memberName = "theMemberName")

        val property = propertyReader.provideStringProperty(ip)

        property shouldBe "yesssss!"
    }

    @Test
    fun `provideStringProperty nonexistant file returns null`() {
        val ip = buildInjectionPoint("öksldfhtest.properties", "hugo")

        val property = propertyReader.provideStringProperty(ip)

        property shouldBe null
    }

    private fun buildInjectionPoint(filename: String, key: String = "", memberName: String? = null): InjectionPoint {
        val propertyFromFile = mockk<PropertyFromFile>()
        every { propertyFromFile.filename } returns filename
        every { propertyFromFile.key } returns key

        val annotated = mockk<Annotated> {
            every { getAnnotation(PropertyFromFile::class.java) } returns propertyFromFile
        }
        val member = if (memberName != null) {
            val m = mockk<Member>()
            every { m.name } returns memberName
            m
        } else {
            null
        }

        val injectionPoint = mockk<InjectionPoint>()
        every { injectionPoint.annotated } returns annotated
        every { injectionPoint.member } returns member
        return injectionPoint
    }
}