plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    api(gimliModule(":api:common"))
}
