plugins {
    id("org.jetbrains.dokka")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
    id("org.jetbrains.kotlinx.kover")
}

buildscript {
    val atomicfu: String by project
    val dokka: String by project
    val kotlin: String by project

    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:$dokka")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:$atomicfu")
    }
}

repositories.mavenCentral()

apiValidation {
    apiDumpDirectory = "abi"
}
