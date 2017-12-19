# CHANGELOG

## V0.4
* CHANGE: refactor maven group and project name for better gradle-composite builds integration

## V0.3
* CHANGE: missing properties file does not cause FileNotFoundException. 
  * `PropertiesFromFile` now returns `Properties()`
  * `PropertyFromFile` now returns `null` as the property value

## V0.2
* BUGFIX: kotlin annotation pathes were wrong

## V0.1
* initial version