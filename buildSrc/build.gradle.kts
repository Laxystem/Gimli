import java.util.*

plugins {
    `kotlin-dsl`
}

repositories.mavenCentral()

val properties = Properties().apply {
    load(File(rootDir.parent, "gradle.properties").inputStream())
}

version = properties["version"]!!

val kotlin: String by properties

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
}
