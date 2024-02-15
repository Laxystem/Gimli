plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    api(gimliModule(":api:server"))
    implementation(gimliModule(":server:database"))
}
