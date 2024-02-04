plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(":api:server"))
    implementation(project(":server:database"))
}
