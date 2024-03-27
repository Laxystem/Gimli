import java.util.*

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

val properties = Properties().apply {
    load(File(rootDir.parent, "gradle.properties").inputStream())
}

version = properties["version"]!!

val abi: String by properties
val dokka: String by properties
val kotlin: String by properties
val kover: String by properties
val licenser: String by properties

dependencies {
    implementation("dev.yumi:yumi-gradle-licenser:$licenser")
    implementation("org.jetbrains.dokka:dokka-base:$dokka")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:$dokka")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlin")
    implementation("org.jetbrains.kotlinx:binary-compatibility-validator:$abi")
    implementation("org.jetbrains.kotlinx:kover-gradle-plugin:$kover")
}
