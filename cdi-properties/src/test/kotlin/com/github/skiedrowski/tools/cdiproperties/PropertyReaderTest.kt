package com.github.skiedrowski.tools.cdiproperties

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.lang.reflect.Member
import javax.enterprise.inject.spi.Annotated
import javax.enterprise.inject.spi.InjectionPoint

class PropertyReaderTest {
    @JvmField
    @Rule
    val thrown: ExpectedException = ExpectedException.none()

    private val propertyReader = PropertyReader(CachingPropertyFileReader())

    @Before
    fun context() {
        System.setProperty(CachingPropertyFileReader.CONFIG_DIR_PROPERTY, "src/test/resources/")
    }

    @Test
    fun provideStringProperty() {
        val ip = buildInjectionPoint("my.properties", "key1")

        val property = propertyReader.provideStringProperty(ip)

        assertThat(property, equalTo("value1"))
    }

    @Test
    fun `provideStringProperty int as string`() {
        val ip = buildInjectionPoint("my.properties", "int1")

        val property = propertyReader.provideStringProperty(ip)

        assertThat(property, equalTo("12345"))
    }

    @Test
    fun `provideStringProperty nonexistant key`() {
        val ip = buildInjectionPoint("my.properties", "oaiwuehf")

        val property = propertyReader.provideStringProperty(ip)

        assertThat(property, absent())
    }

    @Test
    fun provideIntProperty() {
        val ip = buildInjectionPoint("my.properties", "int1")

        val property = propertyReader.provideIntProperty(ip)

        assertThat(property, equalTo(12345))
    }

    @Test
    fun `provideIntProperty nonexistant key`() {
        val ip = buildInjectionPoint("my.properties", "numericdfsdf")

        val property = propertyReader.provideIntProperty(ip)

        assertThat(property, absent())
    }

    @Test
    fun `provideBoolProperty true`() {
        val ip = buildInjectionPoint("my.properties", "bool1")

        val property = propertyReader.provideBoolProperty(ip)

        assertThat(property, equalTo(true))
    }

    @Test
    fun `provideBoolProperty false`() {
        val ip = buildInjectionPoint("my.properties", "bool2")

        val property = propertyReader.provideBoolProperty(ip)

        assertThat(property, equalTo(false))
    }

    @Test
    fun `provideBoolProperty nonexistant key`() {
        val ip = buildInjectionPoint("my.properties", "numericdfsdf")

        val property = propertyReader.provideBoolProperty(ip)

        assertThat(property, absent())
    }

    @Test
    fun `provideProperty no key specified uses member name`() {
        val ip = buildInjectionPoint("my.properties", memberName = "theMemberName")

        val property = propertyReader.provideStringProperty(ip)

        assertThat(property, equalTo("yesssss!"))
    }

    @Test
    fun `provideStringProperty nonexistant file returns null`() {
        val ip = buildInjectionPoint("öksldfhtest.properties", "hugo")

        val property = propertyReader.provideStringProperty(ip)

        assertThat(property, absent())
    }

    private fun buildInjectionPoint(filename: String, key: String = "", memberName: String? = null): InjectionPoint {
        val propertyFromFile = mock<PropertyFromFile> {
            on { this.filename } doReturn filename
            on { this.key } doReturn key
        }

        val annotated = mock<Annotated> {
            on { getAnnotation(PropertyFromFile::class.java) } doReturn propertyFromFile
        }
        val member = if (memberName != null) {
            mock<Member> { on { this.name } doReturn memberName }
        } else {
            null
        }

        return mock {
            on { this.annotated } doReturn annotated
            on { this.member } doReturn member
        }
    }
}