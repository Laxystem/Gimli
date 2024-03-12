plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val ktor: String by project

dependencies {
    api(gimliModule(":api:server"))
    implementation(gimliModule(":server:database"))
}
