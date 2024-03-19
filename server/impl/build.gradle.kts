plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val ktor: String by project

dependencies {
    api(gimliModule(":social:server"))
    implementation(gimliModule(":server:database"))
}
