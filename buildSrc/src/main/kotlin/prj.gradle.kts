//define prior to mvnpublish plugin config!
group = "com.github.skiedrowski.tools.cdi"
//version = "NEXT-SNAPSHOT"
version = "20220905"

plugins {
    id("aspect.java11")
    // provide test fixtures (source set testFixtures) for other tests to reuse,
    // see https://docs.gradle.org/current/userguide/java_testing.html#sec:java_test_fixtures
    `java-test-fixtures`
    id("aspect.kotlin")
    id("aspect.intern.sourcejar")
    id("aspect.intern.testjar")
    id("aspect.intern.javadocjar")
    id("aspect.intern.mvnpublish")
//    id("aspect.intern.mvnpublish_gh")
}

val config = rootProject.extra["config"] as Map<String, *>

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/${System.getProperty("GH_USERNAME")}/${project.rootProject.name}")
        credentials {
            username = System.getProperty("GH_USERNAME")
            password = System.getProperty("GH_TOKEN")
        }
    }
}

dependencies {
    implementation(Deps.kt_tools)
    compileOnly(Deps.cdi_api)

    testFixturesApi(Deps.junit)
    testFixturesApi(Deps.junit_engine)
    testFixturesApi(Deps.cdi_api)
}

tasks.withType<Test> {
    ignoreFailures = config["ignoreTestFailures"] as Boolean
    maxParallelForks = if (filter.includePatterns.isEmpty()) Runtime.getRuntime().availableProcessors() else 1

    useJUnitPlatform()
}
