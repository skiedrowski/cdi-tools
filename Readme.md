# Inject Properties defined in external files using CDI

published in my bintray repo

	repositories {
    	maven { url "https://dl.bintray.com/skiedrowski/maven" }
    }
    	
    dependencies {
     	compile "com.github.skiedrowski.tools.cdi:cdi-properties:$ver.cdi_properties"
    }

Bintray file upload path: 
`com/github/skiedrowski/tools/cdi/MODULE_NAME/VERSION`
`com/github/skiedrowski/tools/cdi/cdi-properties/VERSION`

## Module cdi-properties
### Usage (Kotlin)

Given: a properties file named `my.properties` in the `config dir` of the application with the following contents:

    key1=value1
    key2=value2
    int1=12345
    bool1=true
    bool2=false

The `config dir` is a directory available to the program via the System Property `config.dir`. If this System Property is undefined, the working dir will be used.

#### Use Case 1 Inject the whole properties file
	
	class MyClass 
		@Inject constructor(@param:PropertiesFromFile("my.properties") private val my: Properties) {
	
		fun myFunction() {
			val propertyValue = my["key1"]
			println(propertyValue)
		}
	}
	
	class MyClass {
    	@Inject 
    	@PropertiesFromFile("my.properties") 
    	val my: Properties
    	
    	fun myFunction() {
    		val propertyValue = my["key1"]
    		println(propertyValue)
    	}
    }

This prints "value1" to the console.

#### Use Case 2 Inject a single property

	class MyClass {
	
		@Inject
		@PropertyFromFile("my.properties", "key1")
		lateinit var key1 : String
		
		@Inject
        @PropertyFromFile("my.properties")
        lateinit var bool1 : Boolean
        
        @Inject
        @PropertyFromFile
        lateinit var int1 : Int
	}


The filename is optional (defaults to `config.properties`).
You may use constructor injection or field injection (setter injection is not tested, but should work, too. File a ticket if not.)
