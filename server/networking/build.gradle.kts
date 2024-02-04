plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val ktor by properties
val serialization by properties

dependencies {
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor")
    implementation("io.ktor:ktor-server-netty:$ktor")
    implementation("io.ktor:ktor-server-resources:$ktor")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")
    implementation(project(":server:impl"))
}
