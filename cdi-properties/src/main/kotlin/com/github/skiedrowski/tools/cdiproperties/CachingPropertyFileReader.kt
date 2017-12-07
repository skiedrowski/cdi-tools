package com.github.skiedrowski.tools.cdiproperties

import com.github.skiedrowski.tools.kt.compiler.allopen.AllOpen
import com.github.skiedrowski.tools.kt.compiler.noarg.NoArg
import java.io.File
import java.io.FileInputStream
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@NoArg //or you'll get WELD-001410
@AllOpen //or you'll get WELD-001437
class CachingPropertyFileReader {
    companion object {
        const val CONFIG_DIR_PROPERTY = "config.dir"
    }

    private val propertiesMap = mutableMapOf<String, Properties>()

    fun getProperties(filename: String): Properties {
        return propertiesMap[filename] ?: readPropertiesFromFile(filename)
    }

    private fun readPropertiesFromFile(filename: String): Properties {
        val cfgDir = System.getProperty(CONFIG_DIR_PROPERTY) ?: System.getProperty("user.dir")
        val fullFilename = "$cfgDir/$filename"
        val properties = Properties()
        properties.load(FileInputStream(File(fullFilename)))
        propertiesMap[filename] = properties
        return properties
    }

}