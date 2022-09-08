object Ver {
    const val kotlin = "1.7.10" //also update buildSrc/build.gradle.kts
    const val cdi_api = "2.0" //Java EE 8
    const val kt_tools = "20220905"

    //test
    const val junit = "5.8.+"
    const val mockk = "1.12.7"
    const val kotest_assertions = "5.4.2"
}

object Deps {
    const val kt_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Ver.kotlin}"
    const val kt_tools = "com.github.skiedrowski.tools:kotlin-tools:${Ver.kt_tools}"
    const val cdi_api = "javax.enterprise:cdi-api:${Ver.cdi_api}"

    const val junit = "org.junit.jupiter:junit-jupiter-api:${Ver.junit}"
    const val junit_engine = "org.junit.jupiter:junit-jupiter-engine:${Ver.junit}"

    const val mockk = "io.mockk:mockk:${Ver.mockk}"
    const val kotest_assertions = "io.kotest:kotest-assertions-core:${Ver.kotest_assertions}"
}
